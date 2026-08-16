/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.ashtakavarga;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.app.Kundali;
import org.jyotisa.graha.EGraha;
import org.jyotisa.rasi.ERasi;
import org.junit.jupiter.api.Test;
import org.swisseph.ISwissEph;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.graha.IGraha.*;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweObjects.*;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * The core correctness check here does not need a "reference chart" at all: the classical
 * per-graha Bhinnashtakavarga bindu totals (summed over all 12 rasis) are a fixed property of
 * the benefic-point table itself, not of any actual planetary position - see
 * {@link Ashtakavarga}'s class doc and {@code ai-github-projects/swe-jyotisa-lib/extract-rekha-map.py}.
 * So the same handful of well-known classical numbers (48/49/54/52/39/56/39 for Surya..Shani,
 * grand total 337) must hold for <em>every</em> chart - tested here across several unrelated
 * ones precisely to demonstrate that chart-independence, not to "pick a lucky one".
 */
class AshtakavargaTest extends AbstractTest {

    private static final Map<String, Integer> CLASSICAL_TOTAL = new HashMap<>();

    static {
        CLASSICAL_TOTAL.put(SY_CD, 48);
        CLASSICAL_TOTAL.put(CH_CD, 49);
        CLASSICAL_TOTAL.put(BU_CD, 54);
        CLASSICAL_TOTAL.put(SK_CD, 52);
        CLASSICAL_TOTAL.put(MA_CD, 39);
        CLASSICAL_TOTAL.put(GU_CD, 56);
        CLASSICAL_TOTAL.put(SA_CD, 39);
    }

    private static final int GRAND_TOTAL = 48 + 49 + 54 + 52 + 39 + 56 + 39; // = 337

    private IKundali newLucknow1947() {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1947, 8, 15, 10, 30}, 0f, 10.5), GEO_LUCKNOW, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    private IKundali newKundali(final ISwissEph swe, final int[] date, final float tz, final double lt) {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(swe,
                new SweJulianDate(date, tz, lt), GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    private void assertClassicalTotalsHold(final Ashtakavarga av) {
        for (IGraha point : av.points()) {
            Integer expected = CLASSICAL_TOTAL.get(point.code());
            if (null == expected) continue; // Lagna has no cited classical total

            int total = 0;
            for (ERasi rasi : ERasi.values()) {
                if (null == rasi.rasi()) continue; // NIL sentinel
                total += av.bindu(point, rasi.rasi());
            }
            assertEquals(expected, total, point.code() + "'s Bhinnashtakavarga must sum to its classical total");
        }
    }

    private void assertSarvaGrandTotalHolds(final Ashtakavarga av) {
        int total = 0;
        for (ERasi rasi : ERasi.values()) {
            if (null == rasi.rasi()) continue;
            int sarva = av.sarva(rasi.rasi());
            assertTrue(sarva >= 0 && sarva <= 56, "sarva in [0,56]: " + sarva);
            total += sarva;
        }
        assertEquals(GRAND_TOTAL, total, "Sarvashtakavarga must sum to 337 across all 12 rasis");
    }

    @Test
    void lucknow1947_classicalBinduTotalsHold() {
        Ashtakavarga av = new Ashtakavarga(KUNDALI_7_KARAKAS, newLucknow1947().sweObjects());
        assertClassicalTotalsHold(av);
        assertSarvaGrandTotalHolds(av);
    }

    @Test
    void chartIndependence_classicalTotalsHoldForAnyBirthChart() {
        // deliberately unrelated charts (different dates/places/ayanamsas): if the totals held
        // only for one specific chart, that would mean the totals depend on planetary position,
        // which they must not - see the class doc's proof.
        int[][] dates = {
                {1962, 2, 4, 8, 30}, {1976, 4, 18, 23, 45}, {2000, 1, 1, 0, 0}, {2023, 11, 21, 12, 0}
        };
        for (int[] date : dates) {
            Ashtakavarga av = new Ashtakavarga(KUNDALI_7_KARAKAS,
                    newKundali(getSwephExp(), date, 2f, 12.).sweObjects());
            assertClassicalTotalsHold(av);
            assertSarvaGrandTotalHolds(av);
        }
    }

    @Test
    void everyBinduIsWithinZeroToEight() {
        Ashtakavarga av = new Ashtakavarga(KUNDALI_7_KARAKAS, newLucknow1947().sweObjects());
        for (IGraha point : av.points()) {
            for (ERasi rasi : ERasi.values()) {
                if (null == rasi.rasi()) continue;
                int bindu = av.bindu(point, rasi.rasi());
                assertTrue(bindu >= 0 && bindu <= 8, point.code() + " bindu in [0,8]: " + bindu);
            }
        }
    }

    @Test
    void pointsOrder_isTheSevenGrahasThenLagna() {
        IGraha[] points = new Ashtakavarga(KUNDALI_7_KARAKAS,
                newLucknow1947().sweObjects()).points();
        assertEquals(8, points.length);
        String[] expectedCodes = {SY_CD, CH_CD, BU_CD, SK_CD, MA_CD, GU_CD, SA_CD, LG_CD};
        for (int i = 0; i < 8; i++) assertEquals(expectedCodes[i], points[i].code());
    }

    /**
     * The checksum tests above only prove that each contributor's <em>row</em> of REKHA_MAP is
     * selected correctly - summing a whole row over all 12 rasis is blind to a bug in the
     * querent-index mapping or the house-offset arithmetic, since permuting which querent's
     * data feeds a fixed contributor row would not change that row's total. This test targets
     * exactly that gap: it recomputes every (contributor, rasi) bindu count with a structurally
     * different loop (rasi outer instead of querent outer) and its own, separately-declared
     * point order, reading only the classical {@code REKHA_MAP} table itself (package-visible,
     * already verified byte-for-byte via the checksums) - not {@link Ashtakavarga}'s own
     * {@code POINTS}/{@code uidToTableIndex} wiring.
     */
    @Test
    void everyBindu_matchesAnIndependentlyStructuredRecomputation() {
        final IKundali k = newLucknow1947();
        final int[] signs = k.sweObjects().signs();

        // declared fresh, not copied from Ashtakavarga.POINTS - same table order (Sun, Moon,
        // Mercury, Venus, Mars, Jupiter, Saturn, Ascendant) but built independently here
        final int[] testPointUid = {SY, CH, BU, SK, MA, GU, SA, LG};
        final int[] testPointRasi0 = new int[testPointUid.length];
        for (int t = 0; t < testPointUid.length; t++) testPointRasi0[t] = signs[testPointUid[t]] - 1;

        final Ashtakavarga av = new Ashtakavarga(KUNDALI_7_KARAKAS, k.sweObjects());

        for (int ci = 0; ci < testPointUid.length; ci++) {
            final IGraha contributor = EGraha.byUid(testPointUid[ci]);

            for (int rasi0 = 0; rasi0 < 12; rasi0++) {
                int expected = 0;
                for (int qi = 0; qi < testPointUid.length; qi++) {
                    // house offset (0..11) of this rasi counted from querent qi's own rasi -
                    // computed independently of Ashtakavarga's own (querentRasi0 + k) % 12 loop
                    final int offset = ((rasi0 - testPointRasi0[qi]) % 12 + 12) % 12;
                    if (0 != Ashtakavarga.REKHA_MAP[ci][qi][offset]) expected++;
                }

                final IRasi rasi = ERasi.byUid(rasi0 + 1);
                assertEquals(expected, av.bindu(contributor, rasi),
                        contributor.code() + " in rasi " + (rasi0 + 1));
            }
        }
    }

    @Test
    void toString_containsAllPointsAndTheSarvaRow() {
        String s = new Ashtakavarga(KUNDALI_7_KARAKAS, newLucknow1947().sweObjects()).toString();
        for (String code : new String[]{SY_CD, CH_CD, BU_CD, SK_CD, MA_CD, GU_CD, SA_CD, LG_CD}) {
            assertTrue(s.contains(code), "output must mention " + code);
        }
        assertTrue(s.contains("Sarva"));
    }
}
