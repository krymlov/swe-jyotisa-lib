/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.graha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.dignity.DignityMulatrikona;
import org.jyotisa.dignity.DignityNeecha;
import org.jyotisa.dignity.DignityUccha;
import org.jyotisa.graha.chandra.GrahaChandra;
import org.jyotisa.graha.mangala.GrahaMangala;
import org.jyotisa.graha.surya.GrahaSurya;
import org.jyotisa.varga.VargaD1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * {@code GrahaXxx.dignity(IVarga, double)} - the degree-band formulas that fill in what
 * swe-jyotisa-api leaves entirely abstract. Every graha's dignity is a fixed function of
 * {@code varga.chakraLongitude(longitude)} alone (0-360deg), banded by rasi: exaltation sits
 * in the first ~28-30deg of one specific sign, debilitation in the first ~28-30deg of the
 * opposite sign - a whole-sign-band simplification of the classical "exact degree with orb"
 * exaltation point, not the same convention {@code IGraha.inMrityuBhaga} uses (1deg orb
 * around a single point). Worth knowing before comparing against a reference in step 2: two
 * different "how exalted" conventions coexist in this codebase for different purposes.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class GrahaDignityTest {

    static IDignity dignityOf(IGraha graha, double longitude) {
        return graha.dignity(VargaD1.D1, longitude);
    }

    @Test
    void surya_isExaltedAtTheStartOfMeshaAndDebilitatedAtTheStartOfTula() {
        assertSame(DignityUccha.UCC, dignityOf(GrahaSurya.SY, 5.), "5deg Mesha");
        assertSame(DignityNeecha.NEE, dignityOf(GrahaSurya.SY, 185.), "5deg Tula");
    }

    @Test
    void surya_isInMulatrikonaAtTheStartOfSimhaItsOwnSign() {
        // Surya's mulatrikona is the first 20deg of Simha (its own sign), per the api layer's
        // classical convention that mulatrikona sits within a graha's own rasi
        assertSame(DignityMulatrikona.MLT, dignityOf(GrahaSurya.SY, 130.), "10deg Simha");
    }

    @Test
    void chandra_isExaltedNearThreeDegreesVrishabha() {
        assertSame(DignityUccha.UCC, dignityOf(GrahaChandra.CH, 32.), "2deg Vrishabha");
        assertSame(DignityNeecha.NEE, dignityOf(GrahaChandra.CH, 212.), "2deg Vrischika");
    }

    @Test
    void mangala_isExaltedNearTwentyEightDegreesMakara() {
        assertSame(DignityUccha.UCC, dignityOf(GrahaMangala.MA, 280.), "10deg Makara");
        assertSame(DignityNeecha.NEE, dignityOf(GrahaMangala.MA, 100.), "10deg Karkata, opposite sign");
    }

    @Test
    void outerPlanetsAndLagna_alwaysReturnNull() {
        // no classical dignity rule applies to Lagna or the trans-Saturnian planets - see
        // swe-jyotisa-api's IGrahaSweta/Syama/Teevra/Lagna, which already return null; this
        // pins that swe-jyotisa-lib does not override that with a computed rule either
        assertNull(org.jyotisa.graha.lagna.GrahaLagna.LG.dignity(VargaD1.D1, 100.));
        assertNull(org.jyotisa.graha.sweta.GrahaSweta.SW.dignity(VargaD1.D1, 100.));
        assertNull(org.jyotisa.graha.syama.GrahaSyama.SM.dignity(VargaD1.D1, 100.));
        assertNull(org.jyotisa.graha.teevra.GrahaTeevra.TE.dignity(VargaD1.D1, 100.));
    }

    /**
     * {@code IVarga.chakraLongitude()}'s {@code fid()==1} branch returns the input degree
     * verbatim, with no {@code fix360} - unlike every other varga, which does wrap. A real
     * ecliptic longitude from {@code swe_calc} is always already in [0,360), so this never
     * bites in practice, but it means D1 dignity is NOT robust to an out-of-range input the
     * way D2+ dignity is - pinned here as a documented quirk, not a bug to fix blindly.
     */
    @Test
    void chakraLongitude_forD1_doesNotWrapThreeSixtyBackToZero() {
        assertSame(DignityUccha.UCC, dignityOf(GrahaSurya.SY, 0.));
        assertNull(dignityOf(GrahaSurya.SY, 360.), "360 falls through every band unmatched");
    }
}
