/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.vimsottari;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.vimsottari.EVimsottariYear;
import org.jyotisa.api.vimsottari.IVimsottariPeriod;
import org.jyotisa.refcharts.JhdChart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;
import org.swisseph.app.SweObjectsOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.swisseph.api.ISweJulianDate.SE_GREG_CAL;

import static org.swisseph.app.SweAyanamsa.TRUE_CITRA;
import static org.swisseph.app.SweHouseSystem.WHOLE_SIGN;

/**
 * The Vimsottari dasha against Jagannatha Hora itself, down to the pratyantardasha.
 *
 * <h2>Where the reference numbers come from, and why they had to be timed</h2>
 * Copied from Jagannatha Hora's own Nakshatra Dasha window for {@code ref1970.jhd}, to the second.
 * Date-only output cannot settle any of what is tested here: the whole difference between the
 * candidate rules is smaller than a day at the mahadasha level, and an earlier reading of date-only
 * dumps appeared to prove a Julian year of exactly 365.25 - an artefact of the truncation.
 *
 * <h2>What the timed data settled</h2>
 * <ol>
 *   <li><b>The year is not a length.</b> The Sun's sidereal longitude at all nine antardasha
 *       boundaries inside the Jupiter mahadasha has the same fractional part, {@code .70019}, over
 *       sixteen years - so a period ends when the Sun has travelled {@code years * 360} degrees,
 *       not after a fixed count of days. Individual antardashas deviate from the strictly
 *       proportional division by up to 2.8 days, non-cumulatively, which no fixed year can produce.
 *       See {@link EVimsottariYear#TRUE_SIDEREAL_YEAR}.</li>
 *   <li><b>The Sun is read as a true position, not an apparent one.</b> Read as true, those nine
 *       longitudes agree to 1e-5 degrees; read as apparent they scatter over 0.011 degrees. The
 *       ~20.5 arcsecond aberration is the whole of the difference.</li>
 * </ol>
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class VimsottariRefChartsTest extends AbstractTest {

    /**
     * Jagannatha Hora prints to the second and rounds there; our own Moon differs from its by a
     * fraction of an arcsecond, which moves the whole dasha by a few seconds. Agreement is measured
     * at ten seconds, and the observed worst case is 3.7.
     */
    private static final double TOLERANCE_SECONDS = 10.;

    /** the reference chart's own time zone, since Jagannatha Hora prints local time */
    private static final double REF_TIMEZONE = 5.5 / 24.;

    /** Jupiter mahadasha, {@code start} then {@code close} */
    private static final String[] JHORA_MD = {
            "1959-12-09 01:11:37", "1975-12-09 03:42:15"};

    /** the nine antardashas of the Jupiter mahadasha, each one's start, then its close */
    private static final String[] JHORA_AD = {
            "1959-12-09 01:11:37", "1962-01-24 16:56:33", "1964-08-08 19:44:06",
            "1966-11-15 03:06:05", "1967-10-22 10:01:42", "1970-06-20 02:03:09",
            "1971-04-06 19:50:47", "1972-08-08 20:57:46", "1973-07-15 00:29:35",
            "1975-12-09 03:42:15"};

    /** the nine pratyantardashas of the Jupiter-Jupiter antardasha, then its close */
    private static final String[] JHORA_PD = {
            "1959-12-09 01:11:37", "1960-03-19 05:04:41", "1960-07-23 01:44:29",
            "1960-11-11 10:00:22", "1960-12-25 14:28:39", "1961-05-02 11:28:18",
            "1961-06-11 09:36:13", "1961-08-17 09:10:55", "1961-10-02 11:25:08",
            "1962-01-24 16:56:33"};

    private static VimsottariDasas dasas(final EVimsottariYear year, final int levels) {
        final JhdChart jhd = JhdChart.read("org/jyotisa/refcharts/ref1970.jhd");

        final ISweObjects objects = new SweObjects(getSwephExp(),
                ((SweJulianDate) jhd.julianDate()).calendar(SE_GREG_CAL), jhd.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build()).completeBuild();

        return new VimsottariDasas(objects, levels, year);
    }

    /** a {@code yyyy-MM-dd HH:mm:ss} local moment as a julian day in universal time */
    private static double reference(final String local) {
        final String[] parts = local.split("[- :]");
        final int[] n = new int[6];
        for (int i = 0; i < 6; i++) n[i] = Integer.parseInt(parts[i]);

        return getSwephExp().swe_julday(n[0], n[1], n[2],
                n[3] + n[4] / 60. + n[5] / 3600., swisseph.SweConst.SE_GREG_CAL) - REF_TIMEZONE;
    }

    private static void agrees(final String label, final String jhora, final double ours) {
        final double seconds = (ours - reference(jhora)) * 86400.;
        assertTrue(Math.abs(seconds) <= TOLERANCE_SECONDS,
                () -> label + " is " + String.format("%.1f", seconds) + "s from Jagannatha Hora's "
                        + jhora + ", which is past the " + TOLERANCE_SECONDS + "s tolerance");
    }

    /** the nine boundaries of a level as {@code start}s plus the last one's {@code close} */
    private static void agreesThroughout(final String label, final String[] jhora,
                                         final List<IVimsottariPeriod> ours) {
        assertEquals(9, ours.size(), label + " should hold nine periods");

        for (int i = 0; i < 9; i++) {
            agrees(label + " " + ours.get(i).dasa().code(), jhora[i], ours.get(i).start());
        }
        agrees(label + " close", jhora[9], ours.get(8).close());
    }

    @Test
    @DisplayName("the Jupiter mahadasha begins and ends when Jagannatha Hora says")
    void theMahadashaAgreesWithJagannathaHora() {
        final IVimsottariPeriod guru = dasas(EVimsottariYear.TRUE_SIDEREAL_YEAR, 1).periods().get(0);

        assertEquals("GUVD", guru.dasa().code(), "the 1970 chart's first mahadasha is Jupiter's");
        agrees("mahadasha start", JHORA_MD[0], guru.start());
        agrees("mahadasha close", JHORA_MD[1], guru.close());
    }

    @Test
    @DisplayName("all nine antardashas of that mahadasha agree, to the second")
    void theAntardashasAgreeWithJagannathaHora() {
        agreesThroughout("antardasha", JHORA_AD,
                dasas(EVimsottariYear.TRUE_SIDEREAL_YEAR, 2).periods().get(0).periods());
    }

    @Test
    @DisplayName("all nine pratyantardashas of the Jupiter-Jupiter antardasha agree, to the second")
    void thePratyantardashasAgreeWithJagannathaHora() {
        agreesThroughout("pratyantardasha", JHORA_PD,
                dasas(EVimsottariYear.TRUE_SIDEREAL_YEAR, 3).periods().get(0).periods().get(0).periods());
    }

    /**
     * The measurement that chose the default. A fixed year length is right on average and wrong
     * everywhere in particular: it is out by nearly three days at the mahadasha level here, where
     * the true solar arc is out by three seconds.
     * <p>
     * This is the test that would have to be revisited if the default ever moved, so it states the
     * gap rather than merely tolerating it.
     */
    @Test
    @DisplayName("a fixed year length is days out where the true solar arc is seconds out")
    void aFixedYearLengthIsMeasurablyWorse() {
        final double expected = reference(JHORA_MD[0]);

        final double trueArc = Math.abs(dasas(EVimsottariYear.TRUE_SIDEREAL_YEAR, 1)
                .periods().get(0).start() - expected) * 86400.;
        final double meanYear = Math.abs(dasas(EVimsottariYear.MEAN_SIDEREAL_YEAR, 1)
                .periods().get(0).start() - expected) * 86400.;

        assertTrue(trueArc < 10., "the true solar arc should be seconds out, was " + trueArc);
        assertTrue(meanYear > 86400., "a mean year should be days out, was " + meanYear);
    }

    @Test
    @DisplayName("every level tiles its parent exactly, whatever the rounding")
    void theSubPeriodsTileTheirParent() {
        for (IVimsottariPeriod maha : dasas(EVimsottariYear.TRUE_SIDEREAL_YEAR, 3).periods()) {
            tiles(maha);
        }
    }

    private static void tiles(final IVimsottariPeriod period) {
        final List<IVimsottariPeriod> subs = period.periods();
        if (subs.isEmpty()) return;

        assertEquals(period.start(), subs.get(0).start(), 0.,
                "the first sub-period of " + period.dasa().code() + " must open on its parent");
        assertEquals(period.close(), subs.get(8).close(), 0.,
                "the last sub-period of " + period.dasa().code() + " must close on its parent");

        for (int i = 1; i < subs.size(); i++) {
            assertEquals(subs.get(i - 1).close(), subs.get(i).start(), 0.,
                    "sub-periods of " + period.dasa().code() + " must leave no gap");
            tiles(subs.get(i));
        }
        tiles(subs.get(0));
    }
}
