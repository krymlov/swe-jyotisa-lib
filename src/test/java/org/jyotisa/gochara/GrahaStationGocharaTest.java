/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.gochara;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.app.Kundali;
import org.jyotisa.app.KundaliRuntimeException;
import org.swisseph.api.ISweObjectsOptions;
import org.swisseph.api.ISweStation;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;
import org.swisseph.app.SweStations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.jyotisa.gochara.GrahaStationGochara.Stations.ANY;
import static org.jyotisa.gochara.GrahaStationGochara.Stations.DIRECT;
import static org.jyotisa.gochara.GrahaStationGochara.Stations.RETROGRADE;
import static org.jyotisa.graha.EGraha.*;
import static org.swisseph.app.SweObjectsOptions.TROPICAL_ZODIAC;
import static swisseph.SweConst.SEFLG_SWIEPH;
import static swisseph.SweConst.SEFLG_TRANSIT_LONGITUDE;

/**
 * {@link GrahaStationGochara} - iterating the moments a graha turns retrograde or direct.
 * <p>
 * The strongest check here is not a pinned number but
 * {@link #forwardIterationAgreesWithSweStations()}: {@code org.swisseph.app.SweStations} solves
 * the same problem in a different module with a different loop, and it is itself validated
 * against the published retrograde periods. Two implementations that share only the underlying
 * transit search have to agree, and a defect in the iterator's stepping or in its
 * station-classification shows up there immediately.
 * <p>
 * The published Mercury dates are pinned as well, so the suite still says something without
 * that cross-check.
 */
class GrahaStationGocharaTest extends AbstractTest {

    /** 2000-01-01 00:00 UT, the epoch the published Mercury periods are quoted against */
    private static final double Y2000 = 2451544.5;

    /**
     * Near a station the speed crawls through zero, so the instant is physically determined
     * only to within minutes - this is a tolerance on the search, not a precision claim.
     */
    private static final double ONE_MINUTE = 60. / 86400.;

    /**
     * A chart at the reference epoch. Tropical on purpose: {@link SweStations} is tropical by
     * default, and comparing the two in different frames would compare two different instants -
     * the class doc explains why sidereal moves a station by hours.
     */
    private IKundali chartAt(final double julianDay, final ISweObjectsOptions options) {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(julianDay), GEO_KYIV, options).completeBuild());
    }

    private IKundali tropicalY2000() {
        return chartAt(Y2000, TROPICAL_ZODIAC);
    }

    /**
     * The iterator, with exactly the flags {@link SweStations} uses by default, so the two are
     * comparable instant for instant. The chart's own flags additionally carry
     * {@code SEFLG_TRUEPOS}, which shifts a station slightly.
     */
    private GrahaStationGochara comparable(IKundali kundali, IGraha graha,
                                           boolean forward, GrahaStationGochara.Stations kinds) {
        return new GrahaStationGochara(kundali, graha, forward, kinds) {
            @Override
            public int transitCalcFlags() {
                return SEFLG_SWIEPH | SEFLG_TRANSIT_LONGITUDE;
            }
        };
    }

    /**
     * A julian day as {@code yyyymmdd}.
     * <p>
     * It has to go through {@code ISwissEph}: a {@code SweJulianDate} built from a bare julian
     * day has no date fields until the engine fills them in, and {@code date()} answers
     * {@code null} until then.
     */
    private int day(final double julianDay) {
        final int[] ymd = getSwephExp().getJulianDate(julianDay).date();
        return ymd[0] * 10000 + ymd[1] * 100 + ymd[2];
    }

    // ============================================================ the cross-check

    @Test
    void forwardIterationAgreesWithSweStations() {
        final IKundali kundali = tropicalY2000();
        final SweStations reference = new SweStations(getSwephExp());

        for (IGraha graha : new IGraha[]{BUDHA.graha(), SHUKRA.graha(), MANGALA.graha(),
                GURU.graha(), SHANI.graha()}) {

            final List<ISweStation> expected = reference.between(graha.swefid(), Y2000, Y2000 + 730.);
            assertFalse(expected.isEmpty(), graha.code() + " must have stations in two years");

            final List<GrahaStationEntity> actual = new ArrayList<>();
            final GrahaStationGochara iterator = comparable(kundali, graha, true, ANY);
            for (int i = 0; i < expected.size() && iterator.hasNext(); i++) actual.add(iterator.next());

            assertEquals(expected.size(), actual.size(), graha.code() + " station count");

            for (int i = 0; i < expected.size(); i++) {
                final ISweStation ref = expected.get(i);
                final GrahaStationEntity got = actual.get(i);

                assertEquals(ref.julianDate().julianDay(), got.julianDay(), ONE_MINUTE,
                        graha.code() + " station " + i + " moment");
                assertEquals(ref.retrograde(), got.retrograde(),
                        graha.code() + " station " + i + " kind");
                assertEquals(ref.longitude(), got.longitude(), 1e-4,
                        graha.code() + " station " + i + " longitude");
            }
        }
    }

    // ============================================================ the published reference

    @Test
    void budhaStationsOf2000MatchThePublishedDates() {
        // three retrograde periods: 21 Feb-14 Mar, 23 Jun-17 Jul, 18 Oct-8 Nov
        final int[] expected = {20000221, 20000314, 20000623, 20000717, 20001018, 20001108};
        final boolean[] retrograde = {true, false, true, false, true, false};

        final GrahaStationGochara iterator = comparable(tropicalY2000(), BUDHA.graha(), true, ANY);

        for (int i = 0; i < expected.length; i++) {
            final GrahaStationEntity station = iterator.next();
            assertEquals(expected[i], day(station.julianDay()), "station " + i);
            assertEquals(retrograde[i], station.retrograde(), "kind of station " + i);
        }
    }

    // ============================================================ the filter

    @Test
    void theFilterKeepsOnlyTheRequestedKind() {
        final IKundali kundali = tropicalY2000();

        for (int i = 0; i < 4; i++) {
            assertTrue(comparable(kundali, BUDHA.graha(), true, RETROGRADE).next().retrograde(),
                    "RETROGRADE must never answer a direct station");
        }

        final GrahaStationGochara retro = comparable(kundali, BUDHA.graha(), true, RETROGRADE);
        final GrahaStationGochara direct = comparable(kundali, BUDHA.graha(), true, DIRECT);

        for (int i = 0; i < 4; i++) {
            assertTrue(retro.next().retrograde(), "retrograde-only, result " + i);
            assertFalse(direct.next().retrograde(), "direct-only, result " + i);
        }
    }

    /**
     * Filtering must not invent or drop events: the two filtered streams interleaved have to
     * reproduce the unfiltered one exactly.
     */
    @Test
    void theTwoFilteredStreamsTogetherAreTheUnfilteredOne() {
        final IKundali kundali = tropicalY2000();

        final List<Double> all = new ArrayList<>();
        final GrahaStationGochara any = comparable(kundali, BUDHA.graha(), true, ANY);
        for (int i = 0; i < 6; i++) all.add(any.next().julianDay());

        final List<Double> merged = new ArrayList<>();
        final GrahaStationGochara retro = comparable(kundali, BUDHA.graha(), true, RETROGRADE);
        final GrahaStationGochara direct = comparable(kundali, BUDHA.graha(), true, DIRECT);
        for (int i = 0; i < 3; i++) {
            merged.add(retro.next().julianDay());
            merged.add(direct.next().julianDay());
        }

        // Mercury at this epoch turns retrograde first, so the interleaving starts with retro
        for (int i = 0; i < all.size(); i++) {
            assertEquals(all.get(i), merged.get(i), ONE_MINUTE, "event " + i);
        }
    }

    // ============================================================ direction

    @Test
    void backwardIterationWalksIntoThePastInOrder() {
        final GrahaStationGochara backwards = comparable(tropicalY2000(), BUDHA.graha(), false, ANY);

        double previous = Y2000;
        for (int i = 0; i < 6; i++) {
            final double station = backwards.next().julianDay();
            assertTrue(station < previous,
                    "each backward station must be earlier: " + station + " after " + previous);
            previous = station;
        }
    }

    @Test
    void forwardAndBackwardFromTheSameChartBracketIt() {
        final IKundali kundali = tropicalY2000();

        final double next = comparable(kundali, MANGALA.graha(), true, ANY).next().julianDay();
        final double prev = comparable(kundali, MANGALA.graha(), false, ANY).next().julianDay();

        assertTrue(prev < Y2000, "the backward search must start before the chart: " + prev);
        assertTrue(next > Y2000, "the forward search must start after the chart: " + next);
    }

    // ============================================================ which grahas have stations

    @Test
    void grahasThatNeverReverseAreRejected() {
        final IKundali kundali = tropicalY2000();

        for (IGraha graha : new IGraha[]{SURYA.graha(), CHANDRA.graha(),
                LAGNA.graha(), RAHU.graha(), KETU.graha()}) {

            final KundaliRuntimeException thrown = assertThrows(KundaliRuntimeException.class,
                    () -> new GrahaStationGochara(kundali, graha, true),
                    graha.code() + " has no stations and must be rejected");
            assertTrue(thrown.getMessage().contains(graha.code()), thrown.getMessage());
        }
    }

    @Test
    void theTrueNodeDoesHaveStations() {
        // the mean node never reverses, the true node turns direct for a few days at a time -
        // the check is the calculator's own speed table, not a hard-coded list
        final IKundali kundali = tropicalY2000();
        final GrahaStationGochara iterator =
                comparable(kundali, org.jyotisa.graha.chaya.GrahaRahu.RAHU_TRUE, true, ANY);

        assertTrue(iterator.hasStations());

        double previous = Y2000;
        for (int i = 0; i < 6; i++) {
            final GrahaStationEntity station = iterator.next();
            assertTrue(station.julianDay() > previous,
                    "true node stations must advance, not repeat: " + station.julianDay()
                            + " after " + previous);
            previous = station.julianDay();
        }
    }

    // ============================================================ the entity

    @Test
    void theEntityCarriesTheLongitudeTheGrahaTurnsAt() {
        final GrahaStationEntity station =
                comparable(tropicalY2000(), BUDHA.graha(), true, ANY).next();

        assertNotNull(station.entityEnum());
        assertEquals(BUDHA.graha().code(), station.entityEnum().code());
        assertTrue(station.longitude() >= 0. && station.longitude() < 360.,
                "a real longitude, not the search offset: " + station.longitude());
        assertNotNull(station.pada(), "the entity still behaves like any other gochara entity");
    }

    /**
     * The offset of this search is a <b>speed</b> and stays 0. Getting this wrong is the easy
     * mistake: the inherited {@code getNextOffset} treats a zero step as "keep the entity's
     * longitude", which would move the search off the station condition entirely.
     */
    @Test
    void theSearchOffsetStaysZero() {
        final GrahaStationGochara iterator = comparable(tropicalY2000(), GURU.graha(), true, ANY);

        assertEquals(0., iterator.getStartOffset(), 0.);
        for (int i = 0; i < 3; i++) {
            final GrahaStationEntity station = iterator.next();
            assertEquals(0., iterator.getNextOffset(station), 0.,
                    "the offset must remain the speed 0, not become a longitude");
        }
    }

    // ============================================================ guards

    @Test
    void aNullGrahaOrFilterIsRejected() {
        final IKundali kundali = tropicalY2000();
        assertThrows(KundaliRuntimeException.class,
                () -> new GrahaStationGochara(kundali, null, true));
        assertThrows(KundaliRuntimeException.class,
                () -> new GrahaStationGochara(kundali, BUDHA.graha(), true, null));
    }
}
