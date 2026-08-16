/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.app;

import org.jyotisa.AbstractTest;
import org.junit.jupiter.api.Test;
import org.swisseph.ISwissEph;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweGeoLocation;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * The rise/set lines of the report are rendered to whole seconds, and
 * {@code IDateUtils.format(.., F4Y_2M_2D_2H_2M_2S)} truncates rather than rounds. For the 1970
 * reference chart the computed sunrise is {@code 00:32:21.847564}, so it used to print
 * {@code 00:32:21} while Jagannatha Hora showed {@code 00:32:22} - one second of apparent
 * disagreement about an instant the two agree on to within 0.07 ms (proved by every
 * sunrise-derived point matching to 0.02", including the Vighati Lagna which moves 300"/s).
 * <p>
 * {@link KundaliFields#atWholeSecond} rounds the julian day before it is decomposed, so the
 * carry runs through minutes, hours and the date via the ordinary calendar conversion.
 */
class KundaliFieldsRiseSetRoundingTest extends AbstractTest {

    private static final double SECOND = 1.0 / 86400.0;

    /**
     * The formatter truncates, so what matters is not that the result sits on a second boundary
     * but that <b>truncating it</b> yields {@code round(original)}. Swept across a full second in
     * 1 ms steps, at a realistic julian day where the FP headroom is genuinely tight.
     */
    @Test
    void atWholeSecond_truncatesToTheRoundedSecond_acrossAWholeSecond() {
        final double base = 2440680.5;                       // exactly midnight UT
        for (int ms = 0; ms < 1000; ms++) {
            if (500 == ms) continue;   // see the dedicated tie test below
            final double jd = base + (21 + ms / 1000.0) * SECOND;
            final long expected = Math.round(21 + ms / 1000.0);
            final double snapped = KundaliFields.atWholeSecond(jd);
            final long shown = (long) Math.floor((snapped - base) / SECOND + 1e-9);

            assertEquals(expected, shown, "at +21." + ms + " ms the display must read "
                    + expected + " s, not " + shown);
        }
    }

    /**
     * Exactly half a second is a genuine tie, and at a julian day of ~2.44e6 the boundary is not
     * even representable - {@code base + 21.5 s} multiplied back by 86400 lands a few ulps either
     * side of 21.5. Either neighbouring second is a defensible answer there, so this pins only
     * that the result stays one of the two rather than wandering off; every non-tie millisecond
     * is checked exactly by the sweep above.
     */
    @Test
    void atWholeSecond_atAnExactHalfSecondTieStaysOnOneOfTheTwoNeighbours() {
        final double base = 2440680.5;
        final double snapped = KundaliFields.atWholeSecond(base + 21.5 * SECOND);
        final long shown = (long) Math.floor((snapped - base) / SECOND + 1e-9);

        assertTrue(21 == shown || 22 == shown, "tie must resolve to 21 or 22, got " + shown);
    }

    /** Rounding must never move a value backwards - the 1970 moonset regression. */
    @Test
    void atWholeSecond_neverRoundsDown() {
        final double moonset = 2440680.962061004;   // 1970-04-04 11:05:22.070767 UT
        final double snapped = KundaliFields.atWholeSecond(moonset);
        final double base = 2440680.5;
        final long shown = (long) Math.floor((snapped - base) / SECOND + 1e-9);

        // 11:05:22.07 -> 11:05:22, i.e. the same second, never 21
        assertEquals(Math.round((moonset - base) / SECOND), shown,
                "22.07 s must display as 22, not 21");
    }

    @Test
    void atWholeSecond_carriesAcrossMidnightIntoTheNextDay() {
        // 0.3 s before midnight UT: rounding an isolated SECONDS FIELD would give the impossible
        // 24:00:00; going through the julian day rolls the date instead
        final double justBeforeMidnight = 2440680.5 - 0.3 * SECOND;   // 23:59:59.7 of the prior day
        final double snapped = KundaliFields.atWholeSecond(justBeforeMidnight);

        assertTrue(snapped > 2440680.5, "must snap forward past the day boundary, got " + snapped);
        assertTrue(snapped - 2440680.5 < SECOND, "and no further than into its first second");
    }

    /** The whole point: the printed sunrise now agrees with Jagannatha Hora's 00:32:22 UT. */
    @Test
    void machilipatnam1970_printedSunriseMatchesJagannathaHora() {
        final ISweObjects objects = new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1970, 4, 4, 12, 20, 40}, 0f, 12 + 20 / 60. + 40 / 3600.),
                new SweGeoLocation(81 + 8 / 60., 16 + 10 / 60., 0),
                TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild();

        final String report = new Kundali(KUNDALI_7_KARAKAS, objects).fields().toString();

        assertTrue(report.contains("UTC Sunrise\t\t: 1970–04–04 00:32:22"),
                "sunrise should print rounded (JHora shows 6:02:22 local = 00:32:22 UT); got:\n" + report);
        assertTrue(report.contains("UTC Sunset\t\t: 1970–04–04 12:45:07"),
                "sunset is 12:45:07.219 - rounding must NOT push it to :08; got:\n" + report);
    }

    /** Rounding is display-only: the underlying fields keep their full precision. */
    @Test
    void roundingDoesNotDisturbTheComputedValues() {
        final ISwissEph swe = getSwephExp();
        final ISweObjects objects = new SweObjects(swe,
                new SweJulianDate(new int[]{1970, 4, 4, 12, 20, 40}, 0f, 12 + 20 / 60. + 40 / 3600.),
                new SweGeoLocation(81 + 8 / 60., 16 + 10 / 60., 0),
                TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild();

        final double sunrise = new Kundali(KUNDALI_7_KARAKAS, objects).fields().sunrise();
        assertEquals(2440680.522475088, sunrise, 1e-9,
                "sunrise() must still return the unrounded instant");
    }
}
