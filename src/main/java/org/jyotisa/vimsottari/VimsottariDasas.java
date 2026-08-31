/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.vimsottari.EVimsottariYear;
import org.jyotisa.api.vimsottari.IVimsottariDasa;
import org.jyotisa.api.vimsottari.IVimsottariDasaEnum;
import org.jyotisa.api.vimsottari.IVimsottariDasas;
import org.jyotisa.api.vimsottari.IVimsottariPeriod;
import org.jyotisa.naksatra.ENaksatra;
import org.swisseph.ISwissEph;
import org.swisseph.api.ISweObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.swisseph.api.ISweConstants.NAKSHATRA_LENGTH;
import static org.swisseph.api.ISweObjects.CH;
import static org.swisseph.api.ISweObjects.SY;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * Vimsottari dasha to a chosen depth - see {@link IVimsottariDasas} for the construction and
 * {@link EVimsottariYear} for what a year is measured by.
 *
 * <h2>The whole tree is laid out in years first, and only then dated</h2>
 * Every boundary is a <b>cumulative offset in Vimsottari years</b> from the first mahadasha's start:
 * mahadasha {@code i} begins at the sum of the years before it, antardasha {@code j} inside it at
 * that plus {@code years(i) * before(j) / 120}, and so on down. Those offsets are computed in the
 * years domain, where a period's sub-periods tile it exactly - the ninth is closed at its parent's
 * own close rather than at its own computed end, so rounding cannot accumulate - and only then is
 * each distinct offset converted to a julian day.
 * <p>
 * Doing it that way is what makes {@link EVimsottariYear#TRUE_SIDEREAL_YEAR} affordable and exact at
 * once: the conversion is one function, shared boundaries convert once (a parent's close and its
 * ninth child's close are the same {@code double}, so the cache answers with the same instant), and
 * the conversion is monotone, so a tiling in years is still a tiling in days.
 *
 * <h2>The elapsed fraction is of the naksatra, not of the pada</h2>
 * 13 degrees 20 minutes is the unit. Using the pada's 3 degrees 20 minutes would give a balance four
 * times too small, and on some padas the result looks merely shifted rather than wrong, which is how
 * that mistake hides.
 *
 * @author Yura Krymlov
 * @version 1.1, 2026-08
 */
public class VimsottariDasas implements IVimsottariDasas {
    private static final long serialVersionUID = 8259188117690832217L;

    /**
     * Half a day, used to decide whether a crossing found ahead of a moment <i>is</i> that moment.
     * Consecutive returns of the Sun to one longitude are a year apart, so anything this close is
     * the same crossing seen again rather than the next one.
     */
    protected static final double CROSSING_SLACK = .5;

    /** how far behind a moment to seed a backward search - comfortably more than one year */
    protected static final double CROSSING_LOOKBACK = 380.;

    /** only ever a seed for a forward search, never a period length - see {@link EVimsottariYear} */
    protected static final double SEED_YEAR = 365.256364;

    /** how far ahead of a seed the crossing may still lie; a solar return never strays this far */
    protected static final double SEED_MARGIN = 10.;

    protected final List<IVimsottariPeriod> periods = new ArrayList<>(9);
    protected final int levels;
    protected final EVimsottariYear year;

    /** cumulative years from the first mahadasha's start, to the julian day of that boundary */
    protected final transient Map<Double, Double> dated = new HashMap<>();
    /** the Sun back at its anchor longitude for the k-th time; index 0 is the anchor itself */
    protected final transient double[] anniversaries = new double[(int) VIMSOTTARI_YEARS + 1];

    protected transient ISwissEph swissEph;
    protected transient int solarFlags;
    protected transient double anchor;
    protected transient double anchorSun;
    protected transient int anniversariesKnown;

    /**
     * @param sweObjects a built chart - the Moon, the Sun and the julian day are all this needs
     * @param levels     1 mahadasha, 2 antardasha, 3 pratyantardasha, ... Each level multiplies the
     *                   period count by nine, so ask for what will be read.
     */
    public VimsottariDasas(final ISweObjects sweObjects, final int levels) {
        this(sweObjects, levels, VIMSOTTARI_YEAR);
    }

    /**
     * @param year what a Vimsottari year is measured by - see {@link EVimsottariYear}
     */
    public VimsottariDasas(final ISweObjects sweObjects, final int levels, final EVimsottariYear year) {
        if (levels < 1) throw new IllegalArgumentException("levels must be at least 1, was " + levels);
        this.levels = levels;
        this.year = year;

        final double chandra = sweObjects.longitudes()[CH];
        final double birth = sweObjects.sweJulianDate().julianDay();

        // which naksatra the Moon is in, and how much of it is already spent
        final INaksatra naksatra = ENaksatra.byLongitude(chandra);

        // A chart whose Moon cannot be placed has no Vimsottari dasha at all - the naksatra is what
        // names the first lord and what says how much of it is already spent, and there is nothing
        // to fall back on. A caller marks that by writing NaN into the longitude, which an event
        // with a date but no time has to do: the Moon crosses half a sign in a day. So this leaves
        // `periods` empty and says so, rather than throwing or inventing Ashwini.
        if (naksatra.isNil()) return;

        final double elapsed = (chandra - (naksatra.fid() - 1) * NAKSHATRA_LENGTH) / NAKSHATRA_LENGTH;

        IVimsottariDasaEnum lord = dasaOf(naksatra);

        // the first mahadasha began before birth, by exactly the part already spent
        anchorAt(sweObjects, birth, elapsed * lord.dasa().length());

        double startYears = 0.;

        for (int i = 0; i < 9; i++) {
            final double closeYears = startYears + lord.dasa().length();
            final VimsottariPeriod period = new VimsottariPeriod(
                    lord.dasa(), null, dated(startYears), dated(closeYears), 1);

            divide(period, startYears, closeYears, lord);
            periods.add(period);

            startYears = closeYears;
            lord = lord.following();
        }

        release();
    }

    // ================================================================ the years to days mapping

    /**
     * Fixes where year zero of this chart's dasha falls, and prepares whatever dating it needs.
     * <p>
     * For a fixed year length that is one subtraction. For {@link EVimsottariYear#TRUE_SIDEREAL_YEAR} it
     * is a walk backwards through the Sun's own returns: {@code elapsedYears} whole revolutions of
     * sidereal longitude, and then the remaining fraction of one.
     */
    protected void anchorAt(final ISweObjects sweObjects, final double birth, final double elapsedYears) {
        if (!year.trueSolarArc()) {
            this.anchor = birth - elapsedYears * year.days();
            return;
        }

        this.swissEph = sweObjects.swissEph();
        // The same frame the chart's own Sun was computed in, which is calcFlags rather than
        // mainFlags: sidereal, and TRUE position rather than apparent. Both halves are load-bearing.
        // Sidereal is what makes the fractional part of the Sun's longitude the same at every
        // boundary. True is what Jagannatha Hora uses - measured on its own output, its boundaries
        // agree to 1e-5 degrees when read as true positions and scatter over 0.011 degrees when
        // read as apparent, the ~20.5 arcsecond aberration being the whole of the difference. It is
        // also the only frame consistent with the seed, since longitudes()[SY] is a true position.
        this.solarFlags = sweObjects.sweOptions().calcFlags();

        final double birthSun = sweObjects.longitudes()[SY];
        final int laps = (int) elapsedYears;
        final double fraction = elapsedYears - laps;

        double back = birth;
        for (int i = 0; i < laps; i++) back = previousCrossing(birthSun, back);
        if (0. != fraction) back = previousCrossing(fix360(birthSun - fraction * 360.), back);

        this.anchor = back;
        this.anchorSun = fix360(birthSun - elapsedYears * 360.);
        this.anniversaries[0] = back;
        this.anniversariesKnown = 0;
    }

    /** the julian day of a boundary given as cumulative Vimsottari years from the anchor */
    protected double dated(final double cumulativeYears) {
        if (!year.trueSolarArc()) return anchor + cumulativeYears * year.days();

        final Double known = dated.get(cumulativeYears);
        if (null != known) return known;

        final int laps = (int) cumulativeYears;
        final double fraction = cumulativeYears - laps;
        final double base = anniversary(laps);

        // seed a few days short of the crossing: the search only runs forward, so a seed past the
        // target would answer a year late, while one behind it is merely slower
        final double found = 0. == fraction ? base
                : crossing(fix360(anchorSun + fraction * 360.),
                base + Math.max(0., fraction * SEED_YEAR - SEED_MARGIN));

        dated.put(cumulativeYears, found);
        return found;
    }

    /**
     * The Sun back at the anchor longitude for the k-th time, computed once and kept.
     * <p>
     * Each one is sought from its own approximate place rather than from the one before it, so that
     * the small error of a crossing cannot accumulate along a chain a hundred and twenty long. The
     * seed is the mean sidereal year times k, less a margin: individual returns run a couple of days
     * either side of the mean, but never further, so the margin cannot let a whole return slip past.
     */
    protected double anniversary(final int laps) {
        while (anniversariesKnown < laps) {
            final int k = anniversariesKnown + 1;
            anniversaries[k] = crossing(anchorSun, anchor + k * SEED_YEAR - SEED_MARGIN);
            anniversariesKnown = k;
        }
        return anniversaries[laps];
    }

    /** the first time after {@code from} that the Sun reaches {@code longitude} */
    protected double crossing(final double longitude, final double from) {
        return swissEph.swe_solcross_ut(longitude, from, solarFlags, new StringBuilder());
    }

    /**
     * The last time before {@code before} that the Sun reached {@code longitude}.
     * <p>
     * Searched forwards from well over a year earlier, because that is the only direction
     * {@code swe_solcross_ut} offers. Two candidates then have to be told apart, and the second is
     * taken only when it clears {@code before} by {@link #CROSSING_SLACK} - otherwise it <i>is</i>
     * {@code before}, which happens on every step of the backward walk, since each step starts from
     * a crossing of this very longitude.
     */
    protected double previousCrossing(final double longitude, final double before) {
        final double first = crossing(longitude, before - CROSSING_LOOKBACK);
        final double next = crossing(longitude, first + 1.);
        return next < before - CROSSING_SLACK ? next : first;
    }

    /** drops the scratch state of the construction, which nothing outside it may read */
    protected void release() {
        this.swissEph = null;
        this.dated.clear();
    }

    // ================================================================ the tree

    /** fills a period's sub-periods, and theirs, down to {@link #levels} */
    protected void divide(final VimsottariPeriod period, final double startYears,
                          final double closeYears, final IVimsottariDasaEnum parent) {
        if (period.level() >= levels) return;

        final double span = closeYears - startYears;
        final List<IVimsottariPeriod> subs = period.mutablePeriods(9);

        IVimsottariDasaEnum lord = parent;
        double start = startYears;

        for (int i = 0; i < 9; i++) {
            // the last one closes on the parent, so the nine tile it exactly whatever rounding did
            final double close = i == 8 ? closeYears
                    : start + span * lord.dasa().length() / VIMSOTTARI_YEARS;

            final VimsottariPeriod sub = new VimsottariPeriod(
                    lord.dasa(), period, dated(start), dated(close), period.level() + 1);

            divide(sub, start, close, lord);
            subs.add(sub);

            start = close;
            lord = lord.following();
        }
    }

    /**
     * The dasha whose lord rules this naksatra. Matched by the lord's code rather than by index,
     * because {@code EVimsottariDasa} runs in the Vimsottari order (Surya, Chandra, Mangala, Rahu,
     * ...) while {@code ENaksatra} runs in its own - the two are deliberately not parallel.
     */
    protected static IVimsottariDasaEnum dasaOf(final INaksatra naksatra) {
        final String lord = naksatra.lord().code();

        for (IVimsottariDasaEnum dasa : EVimsottariDasa.values()) {
            if (dasa.isNil()) continue;
            if (lord.equals(dasa.dasa().lord().code())) return dasa;
        }

        throw new IllegalStateException("no vimsottari dasa for the lord of " + naksatra.code());
    }

    /** the registry entry of a dasha leaf */
    protected static IVimsottariDasaEnum dasaOf(final IVimsottariDasa dasa) {
        for (IVimsottariDasaEnum entry : EVimsottariDasa.values()) {
            if (!entry.isNil() && entry.dasa().code().equals(dasa.code())) return entry;
        }
        throw new IllegalStateException("no vimsottari registry entry for " + dasa.code());
    }

    @Override
    public EVimsottariYear year() {
        return year;
    }

    @Override
    public int levels() {
        return levels;
    }

    @Override
    public List<IVimsottariPeriod> periods() {
        return periods;
    }

    @Override
    public IVimsottariPeriod periodAt(final double julianDay) {
        IVimsottariPeriod found = null;
        List<IVimsottariPeriod> level = periods;

        while (!level.isEmpty()) {
            IVimsottariPeriod hit = null;
            for (IVimsottariPeriod period : level) {
                if (period.contains(julianDay)) { hit = period; break; }
            }
            if (null == hit) break;
            found = hit;
            level = hit.periods();
        }

        return found;
    }
}
