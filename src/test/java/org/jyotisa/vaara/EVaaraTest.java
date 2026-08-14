/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.vaara;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * {@code EVaara.byDayOfWeek} - takes the {@code swisseph.SweDate.getDayOfWeekNr(double)}
 * convention (Sunday=0..Saturday=6), which is a <b>different</b> function, with a different
 * numbering, from {@code ISwissEph.swe_day_of_week} (Monday=0..Sunday=6, documented in the
 * workspace CLAUDE.md for swe-java-lib). {@code org.jyotisa.app.Kundali} correctly imports
 * {@code swisseph.SweDate.getDayOfWeekNr}, not the native placalc-family function - this
 * test pins the numbering {@link EVaara#byDayOfWeek} itself actually expects, so a future
 * accidental swap to the native function would be caught by the wrong weekday coming out
 * rather than discovered by a wrong horoscope.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class EVaaraTest {

    @Test
    void byDayOfWeek_zeroIsSunday() {
        assertSame(EVaara.SURYA_VAARA.vaara(), EVaara.byDayOfWeek(0));
    }

    @Test
    void byDayOfWeek_sixIsSaturday() {
        assertSame(EVaara.SHANI_VAARA.vaara(), EVaara.byDayOfWeek(6));
    }

    @Test
    void byDayOfWeek_matchesTheTraditionalWeekdayOrder() {
        assertSame(EVaara.SURYA_VAARA.vaara(), EVaara.byDayOfWeek(0));   // Sunday
        assertSame(EVaara.CHANDRA_VAARA.vaara(), EVaara.byDayOfWeek(1)); // Monday
        assertSame(EVaara.MANGALA_VAARA.vaara(), EVaara.byDayOfWeek(2)); // Tuesday
        assertSame(EVaara.BUDHA_VAARA.vaara(), EVaara.byDayOfWeek(3));   // Wednesday
        assertSame(EVaara.GURU_VAARA.vaara(), EVaara.byDayOfWeek(4));    // Thursday
        assertSame(EVaara.SHUKRA_VAARA.vaara(), EVaara.byDayOfWeek(5));  // Friday
        assertSame(EVaara.SHANI_VAARA.vaara(), EVaara.byDayOfWeek(6));   // Saturday
    }
}
