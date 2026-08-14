/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.karana;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * {@code EKarana.byOffset} - unlike every other family, this is NOT a uniform division. The
 * lunar month's last 18 degrees (342-360, {@code IKaranaEnum.TH14th2ndP00..P18}) are the 3
 * "fixed" karanas Sakuna/Chatushpada/Naga that occur once per month; the karana straddling
 * the month boundary (0-6deg, i.e. 360-366 read as 0-6) is Kimstughna (also fixed, occurs
 * once); everything else (6-342deg) cycles through the 7 "moving" karanas Bava..Vishti 8
 * times over (56 slots / 7 = 8), for the classical 60-karana lunar month
 * (4 fixed + 7 moving x 8 = 60).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class EKaranaTest {

    @Test
    void byOffset_theFourFixedKaranasSitAtTheMonthBoundary() {
        assertSame(EKarana.KIMSTUGHNA.karana(), EKarana.byOffset(0.));
        assertSame(EKarana.KIMSTUGHNA.karana(), EKarana.byOffset(5.999));
        assertSame(EKarana.SAKUNA.karana(), EKarana.byOffset(342.));
        assertSame(EKarana.SAKUNA.karana(), EKarana.byOffset(347.999));
        assertSame(EKarana.CHATUSHPADA.karana(), EKarana.byOffset(348.));
        assertSame(EKarana.NAGA.karana(), EKarana.byOffset(354.));
        assertSame(EKarana.NAGA.karana(), EKarana.byOffset(359.999));
    }

    @Test
    void byOffset_theSevenMovingKaranasCycleThroughTheMiddleOfTheMonth() {
        // first moving karana starts right after Kimstughna, at 6 degrees
        assertSame(EKarana.BAVA.karana(), EKarana.byOffset(6.));
        assertSame(EKarana.BALAVA.karana(), EKarana.byOffset(12.));
        assertSame(EKarana.KAULAVA.karana(), EKarana.byOffset(18.));
        assertSame(EKarana.TAITULA.karana(), EKarana.byOffset(24.));
        assertSame(EKarana.GARIJA.karana(), EKarana.byOffset(30.));
        assertSame(EKarana.VANIJA.karana(), EKarana.byOffset(36.));
        assertSame(EKarana.VISHTI.karana(), EKarana.byOffset(42.));
        // cycles back to Bava after 7 * 6deg = 42deg
        assertSame(EKarana.BAVA.karana(), EKarana.byOffset(48.));
    }

    @Test
    void byOffset_theMovingCycleRepeatsExactlyEightTimesBetweenTheFixedKaranas() {
        // 6..342 degrees is 336 degrees / 6deg per karana = 56 slots = 8 full cycles of 7
        int fullCycles = (342 - 6) / 6 / 7;
        assertEquals(8, fullCycles);
        // the last moving karana before Sakuna (342) must be Vishti (7th of the cycle)
        assertSame(EKarana.VISHTI.karana(), EKarana.byOffset(336.));
    }

    @Test
    void byLongitude_isChandraMinusSurya() {
        assertSame(EKarana.byOffset(20.), EKarana.byLongitude(10., 30.));
    }
}
