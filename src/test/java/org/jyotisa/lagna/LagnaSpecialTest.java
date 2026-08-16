/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.lagna;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.IKundaliFields;
import org.jyotisa.app.Kundali;
import org.junit.jupiter.api.Test;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweObjects.CH;
import static org.swisseph.api.ISweObjects.SY;
import static org.swisseph.api.ISweObjects.LG;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * The five lagnas newly implemented in {@link Lagnas} - vighati/varnada/sree/pranapada/indu -
 * have no maitreya8 reference (it implements only bhava/hora/ghatika, see this project's
 * CLAUDE.md), so they were built against the PyJHora port
 * ({@code e:\Github\PyJHora\src\jhora\panchanga\drik.py} and {@code .\horoscope\chart\charts.py},
 * B.V. Raman's method for Varnada/Indu) plus independently worked classical examples.
 * <p>
 * They were then checked directly against <b>Jagannatha Hora itself</b> on the 1970 reference
 * chart (dumps in {@code jyotisa-uajhora/etc/v8.0/diff/2026uk/}). Varnada, Sree and Indu matched
 * to 0.01" straight away; <b>Pranapada did not</b>, and PyJHora turned out to be the odd one
 * out - it adds the modality offset to the Vighati Lagna, whereas JHora (and the independent
 * swetest reference in {@code відмінність.txt}) re-anchor the swept arc on the <b>birth</b> Sun.
 * That is a ~26' difference, corrected 2026-08-16; see {@link Lagnas#calcPranapada}.
 * <p>
 * This test therefore pins both layers: the isolated formulas against hand-worked numbers, and
 * the Lucknow 1947 fixture against the structural invariants each lagna must satisfy.
 */
class LagnaSpecialTest extends AbstractTest {

    private static IKundali newLucknow1947() {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1947, 8, 15, 10, 30}, 0f, 10.5),
                GEO_LUCKNOW, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    private static IKundali newKundali(final int[] date, final float tz, final double lt) {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(date, tz, lt), GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    // --- calcSree: BPHS-style worked example -------------------------------------------------
    // Lagna 25d05' Virgo (150 + 25d05'), Chandra 13d06' Libra in Swati (180 + 13d06'):
    // remainder within Swati = 386', fraction = 386/800, *360 = 173d42', + Lagna = 348d47' Pisces
    @Test
    void calcSree_matchesTheBphsWorkedExample() {
        final double lagnaLongitude = 150 + 25 + 5 / 60.0;
        final double moonLongitude = 180 + 13 + 6 / 60.0;

        final double sree = Lagnas.calcSree(lagnaLongitude, moonLongitude);

        assertEquals(348 + 47 / 60.0, sree, 0.01);
    }

    // --- calcVarnada: the classical worked example (Lagna Karkata, Hora Lagna Meena) ---------
    // both even signs -> counts add (9+1=10) -> even -> reverse from Meena -> Mithuna
    @Test
    void calcVarnada_matchesTheClassicalWorkedExample() {
        final double lagnaLongitude = 90 + 12.5;   // somewhere in Karkata (Cancer)
        final double horaLongitude = 330 + 20.0;   // somewhere in Meena (Pisces)

        final double varnada = Lagnas.calcVarnada(lagnaLongitude, horaLongitude);

        assertEquals(60, Math.floor(varnada / 30) * 30, "Varnada sign must be Mithuna (Gemini)");
        // the resulting longitude keeps the Lagna's own degree-in-sign
        assertEquals(12.5, varnada % 30, 1e-9);
    }

    @Test
    void calcVarnada_bothOddSigns_stillFollowsLagnaOddityForFinalDirection() {
        // Lagna Mesha (odd), Hora Lagna Simha (odd): counts add (1+5=6, even) but the FINAL
        // direction is decided by Lagna's OWN oddity (odd -> forward from Mesha), not by the
        // combined count's oddity - the detail that distinguishes B.V. Raman's method (JHora's
        // default) from the Sharma/Santhanam alternative also published for Varnada Lagna
        final double lagnaLongitude = 10.0;   // Mesha (Aries), 1st sign, odd
        final double horaLongitude = 130.0;   // Simha (Leo), 5th sign, odd

        final double varnada = Lagnas.calcVarnada(lagnaLongitude, horaLongitude);
        // count1 = 1 (Mesha itself), count2 = 5 (Simha itself), same parity -> count = 6
        // lagna is odd -> forward from Mesha: sign0 = (11+6) mod 12 = 5 = Kanya (Virgo)
        assertEquals(150, Math.floor(varnada / 30) * 30, "Varnada sign must be Kanya (Virgo)");
    }

    // --- calcPranapada: birth-Sun base + the classical modality trisection (0/120/240) -------
    // the swept arc is (vighati - sunAtSunrise); with sunAtSunrise == sunAtBirth the two bases
    // coincide and the result is the plain offset, which isolates the modality rule
    @Test
    void calcPranapada_movableSun_addsNoOffset() {
        final double vighati = 47.0, sun = 15.0;   // Mesha, movable
        assertEquals(fix360(vighati), Lagnas.calcPranapada(vighati, sun, sun));
    }

    @Test
    void calcPranapada_fixedSun_adds240() {
        final double vighati = 47.0, sun = 45.0;   // Vrishabha, fixed
        assertEquals(fix360(vighati + 240), Lagnas.calcPranapada(vighati, sun, sun));
    }

    @Test
    void calcPranapada_dualSun_adds120() {
        final double vighati = 47.0, sun = 75.0;   // Mithuna, dual
        assertEquals(fix360(vighati + 120), Lagnas.calcPranapada(vighati, sun, sun));
    }

    /**
     * The distinguishing property: the base is the Sun at <b>birth</b>, so the Sun's own motion
     * over the ishta kaala carries straight into Pranapada. Taking the sunrise Sun instead (the
     * PyJHora reading this implementation started from) would make Pranapada a fixed rotation of
     * the Vighati Lagna and this difference would vanish.
     */
    @Test
    void calcPranapada_shiftsByTheSunsOwnMotionSinceSunrise() {
        final double vighati = 47.0, sunAtSunrise = 15.0, solarMotion = 0.4847;
        final double sunAtBirth = sunAtSunrise + solarMotion;   // still Mesha, offset stays 0

        assertEquals(fix360(vighati + solarMotion),
                Lagnas.calcPranapada(vighati, sunAtSunrise, sunAtBirth), 1e-9);
    }

    // --- calcIndu: the classical Kala table, cross-checked with an independent recomputation -
    @Test
    void calcIndu_keepsChandrasOwnDegreeInSign() {
        final double lagnaLongitude = 12.3;
        final double moonLongitude = 194.7;

        final double indu = Lagnas.calcIndu(lagnaLongitude, moonLongitude);

        assertEquals(moonLongitude % 30, indu % 30, 1e-9,
                "Indu Lagna must sit at Chandra's own degree-in-sign, only the rasi changes");
    }

    @Test
    void calcIndu_kalaSumIsAMultipleOfTwelve_landsOnTheSignBeforeChandras() {
        // both Lagna and Chandra sit in Meena (Pisces, 0-based sign 11): the 9th-from sign
        // (inclusive count, i.e. 0-based +8) is Vrischika (Scorpio, 0-based 7), lord Kuja,
        // Kala 6, either way - so the Kala sum is 6+6=12, and 12 mod 12 = 0 -> treated as 12
        // -> Indu = 12 signs counted inclusively forward from Chandra's own sign, which wraps
        // exactly one short of a full circle and lands on the sign just before Chandra's own
        final double lagnaLongitude = 330.0;  // Meena (Pisces)
        final double moonLongitude = 330.0;   // same trick: Meena -> 9th-from is also Vrischika

        final double indu = Lagnas.calcIndu(lagnaLongitude, moonLongitude);

        assertEquals(300.0, Math.floor(indu / 30) * 30, "il=12 must land on Kumbha, the sign before Chandra's own Meena");
    }

    // --- Vighati Lagna: same construction as Bhava/Hora/Ghati, just a faster rate -----------
    // rate is 7200 deg/day, i.e. exactly 20x Bhava Lagna's 360 deg/day; since both are of the
    // form fix360(rate*jdsr + lsun), (x - lsun) mod 360 scales linearly with rate for any
    // integer ratio - a chart-independent structural invariant, not a one-chart coincidence
    @Test
    void vighatiLagna_rateIsExactlyTwentyTimesBhavaLagnas_acrossSeveralCharts() {
        final int[][] dates = {
                {1947, 8, 15, 10, 30}, {1962, 2, 4, 8, 30}, {1976, 4, 18, 23, 45}, {2000, 1, 1, 0, 0}
        };
        for (int[] date : dates) {
            final IKundali k = newKundali(date, 2f, 12.0);
            final IKundaliFields fields = k.fields();
            final double lsun = fields.suryaSpashta();
            final double expected = fix360(lsun + 20 * fix360(fields.bhavaLagna() - lsun));
            assertEquals(expected, fields.vighatiLagna(), 1e-6,
                    "Vighati Lagna must be Bhava Lagna's own progression at 20x the rate");
        }
    }

    // --- integration: Lucknow 1947 fixture, hand-verified against the printed report --------
    @Test
    void lucknow1947_allFiveNewLagnasResolveToTheHandVerifiedValues() {
        final IKundali k = newLucknow1947();
        final Lagnas lagnas = (Lagnas) k.lagnas();
        final ISweObjects sweObjects = k.sweObjects();

        final double vighati = lagnas.vighati().longitude();
        final double pranapada = lagnas.pranapada().longitude();
        final double varnada = lagnas.varnada().longitude();
        final double sree = lagnas.sree().longitude();
        final double indu = lagnas.indu().longitude();

        // Pranapada re-anchors the arc the Vighati Lagna has swept since sunrise onto the BIRTH
        // Sun, then adds the modality offset - here 0, since Surya sits in Karkata, a movable
        // sign. So it must lead the Vighati Lagna by exactly the Sun's own motion over the ishta
        // kaala, and must NOT simply equal it.
        final double solarMotionSinceSunrise =
                sweObjects.longitudes()[SY] - k.fields().suryaSpashta();
        assertEquals(fix360(vighati + solarMotionSinceSunrise), pranapada, 1e-6);
        assertTrue(solarMotionSinceSunrise > 0.3,
                "the Sun should have moved appreciably since sunrise: " + solarMotionSinceSunrise);

        // Indu and Varnada must each keep the source point's own degree-in-sign
        assertEquals(sweObjects.longitudes()[CH] % 30, indu % 30, 1e-6);
        assertEquals(sweObjects.longitudes()[LG] % 30, varnada % 30, 1e-6);

        assertTrue(sree >= 0 && sree < 360);
    }
}
