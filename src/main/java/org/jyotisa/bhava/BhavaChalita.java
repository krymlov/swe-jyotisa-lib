/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.bhava.IBhavaChalita;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.graha.EGraha;
import org.swisseph.api.ISweObjects;
import org.swisseph.api.ISweSegment;
import org.swisseph.app.SweSegment;

import java.util.ArrayList;
import java.util.List;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid;
import static org.swisseph.api.ISweConstants.i12;
import static org.swisseph.api.ISweObjects.LAST_OBJECT_ID;
import static org.swisseph.api.ISweObjects.LG;
import static org.swisseph.utils.IDegreeUtils.toDMSms;
import static org.swisseph.utils.IModuloUtils.fix360;
import static swisseph.SweConst.SE_ASC;
import static swisseph.SweConst.SE_MC;

/**
 * The <b>Bhava Chalit</b> chart: Porphyry cusps read as Sripati bhavas.
 *
 * <h2>How the twelve arcs are built</h2>
 * The four angles are known - the ascendant and the midheaven come straight from {@code ascmc},
 * and the nadir and descendant are their opposites. Each of the four quadrants between them is
 * <b>trisected</b>, which gives twelve points; those points are the bhava <b>madhyas</b>, the
 * middles of the bhavas, not their beginnings. A bhava then runs from the midpoint it shares with
 * the previous madhya to the midpoint it shares with the next, so the ascendant sits halfway
 * along the first bhava rather than at its start.
 * <p>
 * The quadrants are unequal away from the equator, so the twelve arcs are unequal too - they
 * simply sum to 360&deg;.
 *
 * <h2>No second ephemeris call</h2>
 * Porphyry is arithmetic on the ascendant and the midheaven, so the whole chart is derived from
 * the two figures {@link ISweObjects} already holds. That is not only cheaper: calling
 * {@code swe_houses_ex} again would have to repeat the tidal-acceleration pinning
 * {@code SweObjects} does around its own call, and a chart whose houses were built with one delta
 * t and whose chalit was built with another would be quietly inconsistent.
 *
 * <h2>Which house system the chart was configured with does not matter</h2>
 * Bhava Chalit is this construction, not "the configured houses". A chart built with whole sign -
 * the default here - still has a real ascendant and midheaven, and those are all this needs. The
 * {@code Bhava} column of the report stays whole sign; the two readings differ on purpose, and
 * {@link IBhavaChalita} says where.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see IBhavaChalita
 */
public class BhavaChalita implements IBhavaChalita {
    private static final long serialVersionUID = 4192806534707261541L;

    /** how many of the four quadrants the twelve bhavas cut each into */
    protected static final int PARTS = 3;

    /** madhya, start and close of each bhava, indexed 1..12 */
    protected final double[] madhyas = new double[i12 + 1];
    protected final double[] starts = new double[i12 + 1];
    protected final double[] closes = new double[i12 + 1];

    /** the chalit bhava of each object of the chart, 1..12, or 0 where it has none */
    protected final int[] bhavas = new int[LAST_OBJECT_ID + 1];

    protected final boolean calculated;

    public BhavaChalita(final ISweObjects sweObjects) {
        final double[] ascmc = sweObjects.ascmc();
        this.calculated = sweObjects.isCalculated(LG);

        if (!calculated) {
            // an ascendant that was never built is 0, and 0 is a perfectly plausible longitude -
            // building from it would produce a chart of twelve real-looking arcs counted from a
            // point that does not exist
            for (int bhava = 1; bhava <= i12; bhava++) {
                madhyas[bhava] = starts[bhava] = closes[bhava] = Double.NaN;
            }
            return;
        }

        trisect(ascmc[SE_ASC], ascmc[SE_MC]);
        bound();
        place(sweObjects);
    }

    /**
     * The twelve madhyas: the four angles, and two more points inside each quadrant.
     * <p>
     * Every arc is taken <b>forward</b> with {@code fix360}, so a quadrant that wraps past
     * 0&deg; - which one of them always does - is trisected the long way round only if that is
     * genuinely the way the houses run. Subtracting instead would silently trisect the
     * complementary 270&deg;.
     */
    protected void trisect(final double ascendant, final double midheaven) {
        final double[] angles = {ascendant, fix360(midheaven + 180.),
                fix360(ascendant + 180.), midheaven};

        for (int quadrant = 0; quadrant < angles.length; quadrant++) {
            final double from = angles[quadrant];
            final double arc = fix360(angles[(quadrant + 1) % angles.length] - from);

            for (int part = 0; part < PARTS; part++) {
                madhyas[quadrant * PARTS + part + 1] = fix360(from + arc * part / PARTS);
            }
        }
    }

    /** a bhava begins and ends halfway to its neighbours */
    protected void bound() {
        for (int bhava = 1; bhava <= i12; bhava++) {
            final int previous = bhava == 1 ? i12 : bhava - 1;
            final int next = bhava == i12 ? 1 : bhava + 1;

            starts[bhava] = midpoint(madhyas[previous], madhyas[bhava]);
            closes[bhava] = midpoint(madhyas[bhava], madhyas[next]);
        }
    }

    /** the point halfway from {@code from} to {@code to}, going forward */
    protected static double midpoint(final double from, final double to) {
        return fix360(from + fix360(to - from) / 2.);
    }

    protected void place(final ISweObjects sweObjects) {
        final double[] longitudes = sweObjects.longitudes();

        for (int id = LG; id <= LAST_OBJECT_ID; id++) {
            bhavas[id] = sweObjects.isCalculated(id) ? bhavaOf(longitudes[id]) : 0;
        }
    }

    /**
     * Which arc a longitude falls in, found by walking the twelve rather than by dividing:
     * the arcs are unequal, so there is nothing to divide by.
     */
    protected int bhavaOf(final double longitude) {
        for (int bhava = 1; bhava <= i12; bhava++) {
            if (fix360(longitude - starts[bhava]) < fix360(closes[bhava] - starts[bhava])) {
                return bhava;
            }
        }
        return 0;   // unreachable while the twelve arcs cover the zodiac
    }

    // ------------------------------------------------------------------ the contract

    @Override
    public boolean isCalculated() {
        return calculated;
    }

    @Override
    public double madhya(final IBhava bhava) {
        return at(madhyas, bhava);
    }

    @Override
    public double start(final IBhava bhava) {
        return at(starts, bhava);
    }

    @Override
    public double close(final IBhava bhava) {
        return at(closes, bhava);
    }

    private static double at(final double[] values, final IBhava bhava) {
        if (null == bhava || bhava.isNil()) return Double.NaN;
        return values[bhava.fid()];
    }

    @Override
    public ISweSegment segment(final IBhava bhava) {
        final double start = start(bhava);
        if (Double.isNaN(start)) return new SweSegment(Double.NaN, Double.NaN);

        return new SweSegment(start, start + length(bhava));
    }

    @Override
    public double length(final IBhava bhava) {
        final double start = start(bhava);
        return Double.isNaN(start) ? Double.NaN : fix360(close(bhava) - start);
    }

    @Override
    public IBhava bhava(final double longitude) {
        if (!calculated || Double.isNaN(longitude)) return EBhava.NIL.bhava();
        return EBhava.byUid(bhavaOf(fix360(longitude)));
    }

    @Override
    public IBhava bhava(final IGraha graha) {
        if (null == graha || !calculated) return EBhava.NIL.bhava();

        final int uid = graha.uid();
        if (uid < LG || uid > LAST_OBJECT_ID) return EBhava.NIL.bhava();

        return EBhava.byUid(bhavas[uid]);
    }

    @Override
    public IGraha[] grahas(final IBhava bhava) {
        if (null == bhava || bhava.isNil() || !calculated) return new IGraha[0];

        final List<IGraha> found = new ArrayList<>();
        for (int id = LG; id <= LAST_OBJECT_ID; id++) {
            if (bhavas[id] == bhava.fid()) found.add(EGraha.byUid(id));
        }

        return found.toArray(new IGraha[0]);
    }

    // ------------------------------------------------------------------ the report

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(700);
        builder.append("Chalit: Porphyry cusps as Sripati bhavas - the madhya is the middle,\n")
                .append("        so the lagna sits halfway along B1, not at its start\n")
                .append("Bhava Start              Madhya             Close              Length  Grahas\n");

        if (!calculated) {
            return builder.append("  not calculated - the chart has no ascendant\n").toString();
        }

        for (int bhava = 1; bhava <= i12; bhava++) {
            final IBhava entry = EBhava.byUid(bhava);

            builder.append(String.format(java.util.Locale.ROOT,
                    "%-4s %-17s %-17s %-17s %6.2f° ",
                    entry.code(), degree(starts[bhava]), degree(madhyas[bhava]),
                    degree(closes[bhava]), length(entry)));

            for (IGraha graha : grahas(entry)) builder.append(' ').append(graha.code());
            builder.append('\n');
        }

        return builder.toString();
    }

    /** {@code "KAN 14°59'32.84\""} - the sign and the degree in it, as the rest of the report reads */
    protected static String degree(final double longitude) {
        return org.jyotisa.rasi.ERasi.byUid(rasiFid(longitude)).label()
                + ' ' + toDMSms(rasiDegree(longitude));
    }
}
