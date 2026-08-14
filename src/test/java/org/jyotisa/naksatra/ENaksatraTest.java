/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.rasi.ERasi;
import org.junit.jupiter.api.Test;

import static org.jyotisa.graha.EGraha.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * {@code ENaksatra.byLongitude}/{@code pada(double)}, {@code ENaksatraPada.byLongitude} plus
 * its {@code rasi()}/{@code navamsa()} lookups, and the 27 concrete {@code Naksatra*}
 * classes' {@code lord()} - which must follow the classical Vimshottari 9-lord cycle
 * (Ketu, Shukra, Surya, Chandra, Mangala, Rahu, Guru, Shani, Budha, repeating 3x).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class ENaksatraTest {

    static final double NAK_LEN = 360. / 27.;

    @Test
    void byLongitude_resolvesEachThirteenTwentySpan() {
        assertSame(ENaksatra.ASHWINI.naksatra(), ENaksatra.byLongitude(0.));
        assertSame(ENaksatra.ASHWINI.naksatra(), ENaksatra.byLongitude(NAK_LEN - 0.001));
        assertSame(ENaksatra.BHARANI.naksatra(), ENaksatra.byLongitude(NAK_LEN));
        assertSame(ENaksatra.REVATI.naksatra(), ENaksatra.byLongitude(359.999));
    }

    @Test
    void lord_followsTheNineGrahaVimsottariCycleThreeTimesOver() {
        // the naksatra-lord table below is read off the actual ENaksatra ordinal order
        // (Ashwini..Revati), repeating the classical 9-graha cycle 3 times over
        ENaksatra[] all = ENaksatra.values();
        IGraha[] expectedLordByOrdinal = {
                KETU.graha(), SHUKRA.graha(), SURYA.graha(), CHANDRA.graha(), MANGALA.graha(),
                RAHU.graha(), GURU.graha(), SHANI.graha(), BUDHA.graha(),
                KETU.graha(), SHUKRA.graha(), SURYA.graha(), CHANDRA.graha(), MANGALA.graha(),
                RAHU.graha(), GURU.graha(), SHANI.graha(), BUDHA.graha(),
                KETU.graha(), SHUKRA.graha(), SURYA.graha(), CHANDRA.graha(), MANGALA.graha(),
                RAHU.graha(), GURU.graha(), SHANI.graha(), BUDHA.graha(),
        };

        for (int i = 1; i <= 27; i++) { // index 0 is NIL
            INaksatra naksatra = all[i].naksatra();
            assertEquals(expectedLordByOrdinal[i - 1].code(), naksatra.lord().code(),
                    all[i].name() + " (naksatra " + i + ") lord");
        }
    }

    @Test
    void pada_staticHelper_cyclesOneToFourWithinEachNaksatra() {
        assertEquals(1, ENaksatra.pada(0.));
        assertEquals(1, ENaksatra.pada(3.3));
        assertEquals(2, ENaksatra.pada(3.4));
        assertEquals(4, ENaksatra.pada(NAK_LEN - 0.01));
        assertEquals(1, ENaksatra.pada(NAK_LEN), "wraps into the next naksatra's first pada");
    }

    @Test
    void naksatraPada_byLongitude_resolvesEachThreeThirtySpan() {
        double padLen = 360. / 108.;
        assertSame(ENaksatraPada.values()[1], ENaksatraPada.byLongitude(0.));
        assertSame(ENaksatraPada.values()[2], ENaksatraPada.byLongitude(padLen));
    }

    @Test
    void naksatraPada_rasiAndNavamsa_areComputedFromItsOwnOrdinal() {
        // ENaksatraPada.rasi() = ERasi.byLongitude((ordinal-1)*NAKSHATRA_PADA_LENGTH)
        // ENaksatraPada.navamsa() = ERasi.byIndex(ordinal)
        ENaksatraPada firstPada = ENaksatraPada.values()[1];
        IRasi rasi = firstPada.rasi();
        IRasi navamsa = firstPada.navamsa();

        assertSame(ERasi.MESHA.rasi(), rasi, "pada 1 sits at longitude 0, in Mesha");
        assertSame(ERasi.MESHA.rasi(), navamsa, "navamsa of pada 1 (index 1) is also Mesha");
    }
}
