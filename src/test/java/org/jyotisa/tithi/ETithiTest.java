/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.tithi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * {@code ETithi.byLongitude}/{@code byOffset} - {@code Tithi = (Chandra - Surya) / 12deg}.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class ETithiTest {

    @Test
    void byOffset_resolvesEachTwelveDegreeSpan() {
        assertSame(ETithi.SHUKLA_PRATIPADA.tithi(), ETithi.byOffset(0.));
        assertSame(ETithi.SHUKLA_PRATIPADA.tithi(), ETithi.byOffset(11.999));
        assertSame(ETithi.SHUKLA_DWITIYA.tithi(), ETithi.byOffset(12.));
        assertSame(ETithi.KRISHNA_AMAVASYA.tithi(), ETithi.byOffset(359.999));
    }

    @Test
    void byLongitude_isChandraMinusSurya() {
        assertSame(ETithi.byOffset(50.), ETithi.byLongitude(10., 60.));
    }

    @Test
    void byOffset_wrapsNegativeOffsets() {
        assertSame(ETithi.byOffset(350.), ETithi.byOffset(-10.));
    }

    @Test
    void code_distinguishesShuklaAndKrishnaUsingUidThirtyBoundary() {
        assertEquals("S15", ETithi.SHUKLA_POORNIMA.code());
        assertEquals("K15", ETithi.KRISHNA_AMAVASYA.code());
        assertEquals(15, ETithi.SHUKLA_POORNIMA.uid());
        assertEquals(30, ETithi.KRISHNA_AMAVASYA.uid());
        // fid stays the shared "type" id (15) for both - see swe-jyotisa-api's CLAUDE.md notes
        assertEquals(15, ETithi.SHUKLA_POORNIMA.tithi().fid());
        assertEquals(15, ETithi.KRISHNA_AMAVASYA.tithi().fid());
    }
}
