/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.upagraha;

import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.app.Kundali;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweObjects.*;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * {@link Upagrahas#calcKalavelaPart} is the "which of the 8 parts of the day (or night) belongs
 * to this graha" arithmetic behind Kaala/Mrityu/Arthaprahaara/Yamaghantaka/Gulika/Maandi.
 * <p>
 * It is verified here against <b>Jagannatha Hora's own part-lord tables</b>, transcribed verbatim
 * from PyJHora (a Python port of JHora) {@code src/jhora/const.py} lines 237-238 - the same
 * reference this project's `ref&lt;year&gt;.jhd` golden charts come from. Those tables, not a
 * paraphrase of them, are the expectation: the production code's closed form is asserted to
 * reproduce every one of the 7 weekdays x 7 grahas x day/night entries.
 * <p>
 * <b>Why table-driven rather than the previous "published panchang table" approach.</b> This
 * test originally checked two hand-transcribed tables and BPHS's Sunday example, and passed
 * while the implementation was wrong. Two reasons it could not catch the bug:
 * <ul>
 * <li>Both BPHS anchors, and the whole published <i>Gulika Kalam</i> column, are about
 *     <b>Saturn</b> - and Saturn's part number happens to be identical under the correct mod-8
 *     rule and the incorrect mod-7 one for every weekday, since {@code 6 - weekday} never leaves
 *     the range 0..6. Saturn simply cannot distinguish the two rules.</li>
 * <li>The <i>Yamagandam Kalam</i> column that would have distinguished them is a
 *     <b>different tradition</b> from the Yamaghantaka upagraha - it is a muhurta
 *     inauspicious-period table, and it disagrees with JHora's Jupiter part on Friday and
 *     Saturday. Checking the upagraha against it was simply the wrong reference.</li>
 * </ul>
 * The real rule: the part lords are an <b>eight</b>-element cycle {Sun, Moon, Mars, Mercury,
 * Jupiter, Venus, Saturn, lordless} rotated so the weekday's own lord leads, so the
 * <b>lordless slot moves with the weekday</b> instead of always being the 8th part. See
 * {@link Upagrahas#calcKalavelaPart}.
 */
class UpagrahaKalavelaTest extends AbstractTest {

    /**
     * Verbatim from PyJHora {@code src/jhora/const.py:237} - {@code day_rulers}. Row = weekday
     * (0..6, Sunday..Saturday), column = part (0..7), value = planet id 0..6 (Sun..Saturn in
     * weekday order) or -1 for the lordless part.
     */
    private static final int[][] DAY_RULERS = {
            {0, 1, 2, 3, 4, 5, 6, -1},
            {1, 2, 3, 4, 5, 6, -1, 0},
            {2, 3, 4, 5, 6, -1, 0, 1},
            {3, 4, 5, 6, -1, 0, 1, 2},
            {4, 5, 6, -1, 0, 1, 2, 3},
            {5, 6, -1, 0, 1, 2, 3, 4},
            {6, -1, 0, 1, 2, 3, 4, 5}
    };

    /** Verbatim from PyJHora {@code src/jhora/const.py:238} - {@code night_rulers}. */
    private static final int[][] NIGHT_RULERS = {
            {4, 5, 6, -1, 0, 1, 2, 3},
            {5, 6, -1, 0, 1, 2, 3, 4},
            {6, -1, 0, 1, 2, 3, 4, 5},
            {0, 1, 2, 3, 4, 5, 6, -1},
            {1, 2, 3, 4, 5, 6, -1, 0},
            {2, 3, 4, 5, 6, -1, 0, 1},
            {3, 4, 5, 6, -1, 0, 1, 2}
    };

    private static int partOf(final int[] rulerRow, final int grahaUid) {
        for (int part = 0; part < rulerRow.length; part++) {
            if (rulerRow[part] == grahaUid - 1) return part;   // uid 1..7 -> planet id 0..6
        }
        return fail("graha uid " + grahaUid + " absent from the ruler row");
    }

    @ParameterizedTest
    @MethodSource("weekdays")
    void calcKalavelaPart_dayParts_matchJhorasOwnDayRulerTable(int weekday) {
        for (int grahaUid = SY; grahaUid <= SA; grahaUid++) {
            assertEquals(partOf(DAY_RULERS[weekday], grahaUid),
                    Upagrahas.calcKalavelaPart(grahaUid, weekday, true),
                    "day part, weekday " + weekday + ", graha uid " + grahaUid);
        }
    }

    @ParameterizedTest
    @MethodSource("weekdays")
    void calcKalavelaPart_nightParts_matchJhorasOwnNightRulerTable(int weekday) {
        for (int grahaUid = SY; grahaUid <= SA; grahaUid++) {
            assertEquals(partOf(NIGHT_RULERS[weekday], grahaUid),
                    Upagrahas.calcKalavelaPart(grahaUid, weekday, false),
                    "night part, weekday " + weekday + ", graha uid " + grahaUid);
        }
    }

    @Test
    void calcKalavelaPart_bphsSundayExamples() {
        // "Gulika is the 7th part of daytime, or the 3rd part of nighttime" - BPHS, for Sunday.
        // Kept as a classical cross-check on the tables above, but note it is Saturn-only and so
        // cannot by itself distinguish the mod-8 rule from the mod-7 one - see the class doc.
        assertEquals(6, Upagrahas.calcKalavelaPart(SA, 0, true), "7th part, 0-indexed = 6");
        assertEquals(2, Upagrahas.calcKalavelaPart(SA, 0, false), "3rd part, 0-indexed = 2");
    }

    @Test
    void calcKalavelaPart_theLordlessSlotRotatesWithTheWeekday() {
        // the specific property the old mod-7 implementation got wrong: the part with no lord is
        // only the 8th one on Sunday. On any other weekday it sits earlier, and some graha owns
        // the 8th part instead. Asserted straight off JHora's table so it states the rule rather
        // than restating the formula.
        for (int weekday = 0; weekday < 7; weekday++) {
            int lordlessPart = -1;
            for (int part = 0; part < 8; part++) {
                if (DAY_RULERS[weekday][part] == -1) lordlessPart = part;
            }
            assertEquals(7 - weekday, lordlessPart,
                    "the lordless day part must step back one place per weekday");

            // ...and no graha ever lands on it
            for (int grahaUid = SY; grahaUid <= SA; grahaUid++) {
                assertNotEquals(lordlessPart, Upagrahas.calcKalavelaPart(grahaUid, weekday, true),
                        "graha uid " + grahaUid + " must not take the lordless part");
            }
        }
    }

    @Test
    void calcKalavelaPart_everyGrahaGetsExactlyOneOfTheEightPartsPerWeekdayAndDayNight() {
        // the 7 grahas SY..SA must occupy 7 distinct parts out of 8, for every weekday and for
        // both day and night - a collision would mean two upagrahas silently share a part
        for (int weekday = 0; weekday < 7; weekday++) {
            for (boolean dayBirth : new boolean[]{true, false}) {
                boolean[] seen = new boolean[8];
                for (int graha = SY; graha <= SA; graha++) {
                    int part = Upagrahas.calcKalavelaPart(graha, weekday, dayBirth);
                    assertTrue(part >= 0 && part < 8, "part out of range: " + part);
                    assertFalse(seen[part], "part " + part + " assigned twice, weekday "
                            + weekday + " dayBirth " + dayBirth);
                    seen[part] = true;
                }
            }
        }
    }

    static IntStream weekdays() {
        return IntStream.range(0, 7);
    }

    // ---- integration: a real chart resolves all 6 Kalavela upagrahas without throwing ----

    private static IKundali newLucknow1947() {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1947, 8, 15, 10, 30}, 0f, 10.5),
                GEO_LUCKNOW, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    @Test
    void lucknow1947_allSixKalavelaUpagrahasResolveToDistinctSaneValues() {
        IKundali k = newLucknow1947();

        IUpagrahaEntity kaala = k.upagrahas().kaala();
        IUpagrahaEntity mrityu = k.upagrahas().mrityu();
        IUpagrahaEntity arthaprahaara = k.upagrahas().arthaprahaara();
        IUpagrahaEntity yamaghantaka = k.upagrahas().yamaghantaka();
        IUpagrahaEntity gulika = k.upagrahas().gulika();
        IUpagrahaEntity maandi = k.upagrahas().maandi();

        for (IUpagrahaEntity e : new IUpagrahaEntity[]{kaala, mrityu, arthaprahaara, yamaghantaka, gulika, maandi}) {
            assertNotNull(e);
            assertTrue(e.longitude() >= 0 && e.longitude() < 360, "longitude in range: " + e.longitude());
            assertNotNull(e.bhava(), "bhava resolves for " + e.entityEnum().code());
            assertNotNull(e.pada(), "naksatra pada resolves for " + e.entityEnum().code());
        }

        // Gulika (start of Saturn's part) and Maandi (middle of the very same part) must not
        // coincide - if they did, the atMiddleOfPart distinction silently stopped doing anything
        assertTrue(Math.abs(gulika.longitude() - maandi.longitude()) > 1e-9,
                "Gulika and Maandi must differ despite sharing Saturn's part");
    }

    @Test
    void lucknow1947_kalavelaUpagrahasAreIndependentOfCallOrder() {
        // all[] is populated once in the constructor; calling the getters in a different
        // order (or twice) must not recompute or mutate anything
        IKundali k = newLucknow1947();

        double first = k.upagrahas().gulika().longitude();
        double second = k.upagrahas().maandi().longitude();
        double third = k.upagrahas().gulika().longitude();

        assertEquals(first, third, 0.0, "repeated gulika() must return the same value");
        assertTrue(Math.abs(first - second) > 1e-9, "gulika/maandi remain distinct regardless of call order");
    }
}
