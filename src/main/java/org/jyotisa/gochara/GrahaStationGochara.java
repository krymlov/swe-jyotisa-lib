/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.gochara;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.app.KundaliIterator;
import org.jyotisa.app.KundaliRuntimeException;
import swisseph.TCPlanet;
import swisseph.TransitCalculator;

import java.util.NoSuchElementException;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.swisseph.api.ISweConstants.DELTA_D0000001;
import static org.swisseph.api.ISweConstants.d0;
import static swisseph.SweConst.SEFLG_SPEED;
import static swisseph.SweConst.SEFLG_TRANSIT_SPEED;

/**
 * Iterates the <b>stations</b> of a graha - the moments it turns retrograde or direct - forwards
 * or backwards in time, optionally restricted to one of the two kinds.
 * <p>
 * A station is where the graha's speed in longitude crosses zero, so it is exactly the transit
 * of that speed over the value 0. This class drives the same
 * {@code SEFLG_TRANSIT_LONGITUDE | SEFLG_TRANSIT_SPEED} search that
 * {@link org.swisseph.app.SweStations} does, but as a {@link KundaliIterator} so it composes
 * with the rest of the gochara family:
 *
 * <pre>
 * // every station of Guru from the chart's moment onwards
 * GrahaStationGochara it = new GrahaStationGochara(kundali, GURU, true);
 * while (it.hasNext()) { GrahaStationEntity station = it.next(); ... }
 *
 * // only the moments Budha turns retrograde, going back in time
 * new GrahaStationGochara(kundali, BUDHA, false, Stations.RETROGRADE);
 * </pre>
 *
 * <h2>Which grahas have stations</h2>
 * Surya, Chandra and the <b>mean</b> lunar nodes never reverse, and Lagna is not a body at all,
 * so none of them has stations. The constructor rejects them rather than starting a search that
 * cannot converge. The <b>true</b> node does have stations - {@code GrahaRahu.RAHU_TRUE} maps to
 * {@code SE_TRUE_NODE} - and they come in closely spaced pairs, because it turns direct for only
 * a few days at a time. That case is what {@link #SEARCH_STEP} is sized for.
 * <p>
 * The test is not a hard-coded list: it asks the calculator whether zero lies between the
 * graha's tabulated extreme speeds, so it stays correct if a graha's mapping changes.
 *
 * <h2>The frame matters, and this class follows the chart</h2>
 * The transit search uses {@link #transitCalcFlags()} like every other iterator here, so a
 * sidereal chart looks for the zero of the <b>sidereal</b> speed. That is <i>not</i> the same
 * instant as the conventional (tropical) station: the ayanamsa moves about 3.8e-5 deg/day, and
 * for a slow graha whose speed crawls through zero that shifts the station by hours.
 * <p>
 * Following the chart is the deliberate choice - it is what the rest of this package does, and
 * silently computing in a different frame than the chart the caller built would be worse. If
 * you want the conventional tropical station, clear the bit:
 *
 * <pre>
 * new GrahaStationGochara(kundali, graha, true) {
 *     &#64;Override public int transitCalcFlags() {
 *         return super.transitCalcFlags() &amp; ~SEFLG_SIDEREAL;
 *     }
 * };
 * </pre>
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see org.swisseph.app.SweStations
 * @see GrahaStationEntity
 */
public class GrahaStationGochara extends KundaliIterator<GrahaStationEntity> {

    /** which stations to report */
    public enum Stations {
        /** only the moments the graha turns retrograde */
        RETROGRADE,
        /** only the moments the graha turns direct */
        DIRECT,
        /** every station, both kinds, in the order they occur */
        ANY;

        public boolean matches(final boolean retrograde) {
            return this == ANY || (retrograde ? this == RETROGRADE : this == DIRECT);
        }
    }

    protected static final String NO_STATIONS = " never reverses, so it has no stations";

    /**
     * How far past a found station the next search starts.
     * <p>
     * The inherited {@link KundaliIterator#calcNextTransit()} nudges by
     * {@link org.swisseph.api.ISweConstants#DELTA_D0000001} - under a hundredth of a second -
     * which is fine when the next event is a whole segment away but not here: the interpolated
     * answer can land a hair before the crossing, and a nudge that small would find the same
     * station again and again. Stations are never closer together than a few days, even for the
     * true node whose direct spells are the shortest, so this cannot skip one.
     */
    protected static final double SEARCH_STEP = 0.05;

    /** offset used to look at the speed just before and just after the station */
    protected static final double SPEED_PROBE = 0.25;

    /**
     * Stations strictly alternate retrograde, direct, retrograde, ... so a filtered iterator
     * needs to skip at most one. This bound is far above that: reaching it means the search
     * stopped advancing, which is a defect rather than an exhausted range.
     */
    protected static final int MAX_SKIPPED = 16;

    protected final IGraha graha;
    protected final Stations stations;

    public GrahaStationGochara(IKundali kundali) {
        this(kundali, CHANDRA, true);
    }

    public GrahaStationGochara(IKundali kundali, boolean forward) {
        this(kundali, CHANDRA, forward);
    }

    public GrahaStationGochara(IKundali kundali, IGraha graha, boolean forward) {
        this(kundali, graha, forward, Stations.ANY);
    }

    /**
     * @param kundali the chart whose moment the search starts from, and whose options supply
     *            the ephemeris and the frame
     * @param graha which graha's stations to iterate; must be one that reverses
     * @param forward {@code true} to walk forwards in time, {@code false} backwards
     * @param stations which of the two kinds to report
     * @throws KundaliRuntimeException if the graha has no stations
     */
    public GrahaStationGochara(IKundali kundali, IGraha graha, boolean forward, Stations stations) {
        // the offset of a station search is a speed, and it stays 0 for every step - so there
        // is no per-step offset to advance, unlike a longitude gochara
        super(kundali, forward, d0);

        if (null == graha) throw new KundaliRuntimeException("graha cannot be NULL");
        if (null == stations) throw new KundaliRuntimeException("stations cannot be NULL");

        this.graha = graha;
        this.stations = stations;

        if (!hasStations()) {
            throw new KundaliRuntimeException(graha.code() + NO_STATIONS);
        }
    }

    // ------------------------------------------------------------------ the iterator contract

    /**
     * A calculator for the transit of the longitudinal speed over zero, i.e. for a station.
     * <p>
     * {@code SEFLG_TRANSIT_LONGITUDE} is already in the chart's transit flags; what makes this
     * a station search rather than a longitude search is {@code SEFLG_TRANSIT_SPEED}.
     */
    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanet(swissEph, graha.swefid(),
                transitCalcFlags() | SEFLG_TRANSIT_SPEED, startOffset);
    }

    /** a station is speed zero, so the offset the search looks for is 0 */
    @Override
    public double getStartOffset() {
        return d0;
    }

    /**
     * Also 0, and that is the reason this class passes {@code offsetStep = 0} to the
     * superclass but cannot use its {@link KundaliIterator#getNextOffset(org.jyotisa.api.IKundaliEntity)}:
     * that method treats a zero step as "stay on the entity's <i>longitude</i>", which is right
     * for a longitude gochara and wrong here, where the offset is a speed.
     */
    @Override
    protected double getNextOffset(final GrahaStationEntity entity) {
        return d0;
    }

    /**
     * The first {@code next()} answers the <b>next</b> station in the direction of travel, not
     * the most recent past one. A station is an instant rather than a segment, so there is no
     * "the one we are currently inside" to align to - which is what the inherited
     * implementation would look for.
     */
    @Override
    protected void calcFirstTransit() {
        julianDay = forward ? (julianDay - DELTA_D0000001) : (julianDay + DELTA_D0000001);
        julianDay = TransitCalculator.getTransitUT(transitCalc, julianDay, !forward);
        alignTransit = false;
    }

    /** steps a real distance past the station just found - see {@link #SEARCH_STEP} */
    @Override
    protected void calcNextTransit() {
        julianDay = forward ? (julianDay + SEARCH_STEP) : (julianDay - SEARCH_STEP);
        julianDay = TransitCalculator.getTransitUT(transitCalc, julianDay, !forward);
    }

    @Override
    public GrahaStationEntity newTransitEntity() {
        // the calculator's offset is the speed being crossed (0), not a longitude, so the
        // position has to be computed rather than read off it as a longitude gochara does
        final double[] at = position(julianDay);

        // at the station the speed is zero by construction, so its sign says nothing about
        // which kind of station this is - sample both sides instead
        final double before = position(julianDay - SPEED_PROBE)[3];
        final double after = position(julianDay + SPEED_PROBE)[3];

        return new GrahaStationEntity(at[0], graha, julianDay, after < before);
    }

    /**
     * The next station matching {@link #stations}, skipping the other kind.
     *
     * @throws NoSuchElementException if the search leaves the ephemeris range first
     */
    @Override
    public GrahaStationEntity next() {
        for (int skipped = 0; skipped <= MAX_SKIPPED; skipped++) {
            if (!hasNext()) {
                throw new NoSuchElementException("no further " + stations
                        + " station of " + graha.code() + " within the ephemeris range");
            }

            final GrahaStationEntity entity = super.next();
            if (stations.matches(entity.retrograde())) return entity;
        }

        throw new KundaliRuntimeException("the station search for " + graha.code()
                + " stopped advancing after " + MAX_SKIPPED + " results");
    }

    // ------------------------------------------------------------------ queries

    /**
     * Whether this graha reverses direction at all, asked of the calculator rather than of a
     * hard-coded list: a station exists only if zero lies between the tabulated extreme speeds.
     */
    public boolean hasStations() {
        try {
            final TransitCalculator calc = createTransitCalc(d0);
            return calc.getMinOffset() < d0 && calc.getMaxOffset() > d0;
        } catch (RuntimeException notSupported) {
            // an object Swiss Ephemeris has no speed table for, or none at all - Lagna's
            // swefid() is ERR
            return false;
        }
    }

    public IGraha getGraha() {
        return graha;
    }

    public Stations getStations() {
        return stations;
    }

    // ------------------------------------------------------------------ internals

    /**
     * {@code swe_calc()} at a universal time, with speed, in the chart's own frame.
     * <p>
     * Uses the chart's {@code calcFlags()} - the same ones {@code SweObjects} builds planet
     * positions with - rather than the transit flags, which carry {@code SEFLG_TRANSIT_*} bits
     * that mean nothing to {@code swe_calc}.
     */
    protected double[] position(final double jdUT) {
        final double[] xx = new double[6];
        final StringBuilder serr = new StringBuilder();
        final double jdET = jdUT + swissEph.swe_deltat(jdUT);

        if (swissEph.swe_calc(jdET, graha.swefid(),
                kundali.sweOptions().calcFlags() | SEFLG_SPEED, xx, serr) < 0) {
            throw new KundaliRuntimeException("swe_calc failed for " + graha.code()
                    + " at JD " + jdET + ": " + serr);
        }

        return xx;
    }
}
