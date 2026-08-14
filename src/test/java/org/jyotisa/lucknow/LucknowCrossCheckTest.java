/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.lucknow;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.app.Kundali;
import org.jyotisa.bindu.BhriguBindu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.jyotisa.app.KundaliOptions.KUNDALI_8_KARAKAS;
import static org.jyotisa.karaka.ECharaKaraka.ATMA_KARAKA;
import static org.jyotisa.karaka.ECharaKaraka.DARA_KARAKA;
import static org.jyotisa.karaka.ECharaKaraka.PITRI_KARAKA;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.api.ISweObjects.SY;
import static org.swisseph.app.SweObjectsOptions.LAHIRI_AYANAMSA;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * Same 1947-08-15 10:30 Lucknow instant {@link LucknowTest} pins as a golden-file dump, but
 * exercised here with real computed assertions against {@link BhriguBindu}, {@code
 * Upagrahas}' chained-offset formulas, {@link IPanchanga}'s codes, and {@link
 * Kundali#grahas()}'s Chara Karaka assignment - independently cross-checked against values
 * already pinned in {@code LUCKNOW1947_A01.txt} ({@code Bhrigu Bindu: 69°32'53.15", Bhava:
 * B7}; {@code Vaara: SKVR, Tithi: K14, Nitya Yoga: VYAT, Karana: SKN}).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
@Execution(ExecutionMode.SAME_THREAD)
class LucknowCrossCheckTest extends AbstractTest {
    static final int[] DATE_1947 = {1947, 8, 15, 10, 30};

    IKundali newLucknow1947(org.jyotisa.api.IKundaliOptions options) {
        ISweObjects sweObjects = new SweObjects(getSwephExp(), new SweJulianDate(DATE_1947, 0f, 10.5),
                GEO_LUCKNOW, LAHIRI_AYANAMSA).completeBuild();
        return new Kundali(options, sweObjects);
    }

    @Test
    void panchanga_codesMatchTheGoldenFile() {
        IPanchanga panchanga = newLucknow1947(KUNDALI_7_KARAKAS).panchanga();
        assertEquals("SKVR", panchanga.vaara().code());
        assertEquals("K14", panchanga.tithi().code());
        assertEquals("NY17", panchanga.yoga().code(), "Vyatipata is nitya yoga #17");
        assertEquals("KR8", panchanga.karana().code(), "Sakuna is karana #8");
    }

    @Test
    void bhriguBindu_bhavaMatchesTheGoldenFile() {
        IKundali kundali = newLucknow1947(KUNDALI_7_KARAKAS);
        BhriguBindu bindu = new BhriguBindu(kundali);
        assertEquals("B7", bindu.bhava().code());
    }

    @Test
    void bhriguBindu_isTheMidpointOfChandraAndRahu() {
        IKundali kundali = newLucknow1947(KUNDALI_7_KARAKAS);
        double chandra = kundali.grahas().chandra().longitude();
        double rahu = kundali.grahas().rahu().longitude();

        BhriguBindu bindu = new BhriguBindu(kundali);
        // the midpoint formula does not itself normalize past 360, unlike most other
        // formulas in this codebase - mirrored here rather than using fix360 blindly
        assertEquals((chandra + rahu) / 2., bindu.longitude(), 1e-9);
    }

    @Test
    void upagrahas_chainedOffsetsAreInternallyConsistent() {
        IKundali kundali = newLucknow1947(KUNDALI_7_KARAKAS);
        double dhuma = kundali.upagrahas().dhuma().longitude();
        double vyatipaata = kundali.upagrahas().vyatipaata().longitude();
        double parivesha = kundali.upagrahas().parivesha().longitude();
        double indrachaapa = kundali.upagrahas().indrachaapa().longitude();

        assertEquals(fix360(360. - dhuma), vyatipaata, 1e-6, "Vyatipata = 360 - Dhuma");
        assertEquals(fix360(vyatipaata + 180.), parivesha, 1e-6, "Parivesha = Vyatipata + 180");
        assertEquals(fix360(360. - parivesha), indrachaapa, 1e-6, "Indrachaapa = 360 - Parivesha");
    }

    @Test
    void upagrahas_dhumaIsSuryaPlusOneThreeThreeDegreesTwenty() {
        IKundali kundali = newLucknow1947(KUNDALI_7_KARAKAS);
        double surya = kundali.grahas().surya().longitude();
        double dhuma = kundali.upagrahas().dhuma().longitude();
        assertEquals(fix360(surya + 133. + 20. / 60.), dhuma, 1e-6);
    }

    /**
     * In 7-karaka mode, {@code Kundali.grahas()} never adds Rahu's entity to the list it
     * sorts and ranks (only Surya..Shani, indices [SY, RA)) - so Rahu's {@code charaKaraka()}
     * stays at its unset default, {@code null}, not "some karaka". Rank #5 (Pitri Karaka) is
     * also explicitly skipped when numbering the 7 that ARE ranked, so ranks 1-4 and 6-8 are
     * used, never 5.
     */
    @Test
    void charaKaraka_sevenKarakaScheme_leavesRahuUnrankedAndSkipsPitriRank() {
        IKundali kundali = newLucknow1947(KUNDALI_7_KARAKAS);
        IGrahaEntity[] all = kundali.grahas().all();

        assertNull(all[RA].charaKaraka(), "Rahu is never added to the ranked list in 7-karaka mode");

        TreeSet<Integer> ranksUsed = new TreeSet<>();
        for (int i = SY; i < RA; i++) {
            ICharaKaraka ck = all[i].charaKaraka();
            assertTrue(null != ck, "every one of Surya..Shani must be ranked");
            ranksUsed.add(ck.uid());
        }

        assertEquals(7, ranksUsed.size(), "7 distinct ranks used");
        assertTrue(!ranksUsed.contains(PITRI_KARAKA.uid()), "rank 5 (Pitri Karaka) must never be used");
        for (int rank = ATMA_KARAKA.uid(); rank <= DARA_KARAKA.uid(); rank++) {
            if (rank == PITRI_KARAKA.uid()) continue;
            assertTrue(ranksUsed.contains(rank), "rank " + rank + " must be used exactly once");
        }
    }

    /**
     * In 8-karaka mode Rahu IS added to the ranked list (indices [SY, RA] inclusive), and
     * every rank 1-8, including Pitri Karaka (#5), is used exactly once.
     */
    @Test
    void charaKaraka_eightKarakaScheme_ranksAllEightIncludingRahuAndPitri() {
        IKundali kundali = newLucknow1947(KUNDALI_8_KARAKAS);
        IGrahaEntity[] all = kundali.grahas().all();

        TreeSet<Integer> ranksUsed = new TreeSet<>();
        for (int i = SY; i <= RA; i++) {
            ICharaKaraka ck = all[i].charaKaraka();
            assertTrue(null != ck, "every one of Surya..Shani and Rahu must be ranked");
            ranksUsed.add(ck.uid());
        }

        assertEquals(8, ranksUsed.size(), "8 distinct ranks used");
        for (int rank = ATMA_KARAKA.uid(); rank <= DARA_KARAKA.uid(); rank++) {
            assertTrue(ranksUsed.contains(rank), "rank " + rank + " must be used exactly once");
        }
    }
}
