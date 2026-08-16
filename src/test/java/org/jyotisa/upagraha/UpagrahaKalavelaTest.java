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
import static org.swisseph.app.SweObjectsOptions.LAHIRI_AYANAMSA;

/**
 * {@link Upagrahas#calcKalavelaPart} is the classical "which of the 7 lorded parts of the day
 * (or night) belongs to this graha" arithmetic behind Kaala/Mrityu/Arthaprahaara/Yamaghantaka/
 * Gulika/Maandi. It is verified here two ways, deliberately not by re-reading the source:
 * <p>
 * 1. Against BPHS's own two worked examples (3.68 and its context): for a Sunday birth, Gulika
 *    (Saturn's part) is "the 7th part of daytime, or the 3rd part of nighttime".
 * 2. Against the standard published Gulika-Kalam and Yamagandam part-number tables (identical
 *    across every Indian panchang site/app) for all 7 weekdays, transcribed independently below
 *    rather than derived from the same modular formula the production code uses - so a sign or
 *    off-by-one error in that formula cannot hide behind a shared derivation.
 * <p>
 * Both independently-sourced tables turn out to satisfy the exact same closed form
 * (verified by hand while writing this test, not assumed): for a day birth, graha G's part
 * (1-indexed) is {@code ((indexInWeekdayOrder(G) - weekday) mod 7) + 1}, where the "weekday
 * order" is simply Sun, Moon, Mars, Mercury, Jupiter, Venus, Saturn (i.e. graha uid 1..7, since
 * {@link org.swisseph.api.ISweObjects}'s SY..SA already are 1..7 in that exact order) - which is
 * exactly why {@link Upagrahas#calcKalavelaPart} can compute it directly from a graha uid without
 * a lookup table. That closed form is not re-derived here; the two source-independent tables are.
 */
class UpagrahaKalavelaTest extends AbstractTest {

    // 1-indexed part number, Sunday..Saturday (weekday 0..6), as published for "Gulika Kalam"
    private static final int[] DAY_GULIKA_PART_1INDEXED = {7, 6, 5, 4, 3, 2, 1};
    // 1-indexed part number, Sunday..Saturday, as published for "Yamagandam" (~Yamaghantaka)
    private static final int[] DAY_YAMAGHANTAKA_PART_1INDEXED = {5, 4, 3, 2, 1, 7, 6};
    // derived from the BPHS Sunday example ("7th part of daytime, or the 3rd part of
    // nighttime") applied consistently to the rest of the week
    private static final int[] NIGHT_GULIKA_PART_1INDEXED = {3, 2, 1, 7, 6, 5, 4};

    @ParameterizedTest
    @MethodSource("weekdays")
    void calcKalavelaPart_dayGulika_matchesThePublishedGulikaKalamTable(int weekday) {
        int expected0Indexed = DAY_GULIKA_PART_1INDEXED[weekday] - 1;
        assertEquals(expected0Indexed, Upagrahas.calcKalavelaPart(SA, weekday, true),
                "day Gulika part for weekday " + weekday);
    }

    @ParameterizedTest
    @MethodSource("weekdays")
    void calcKalavelaPart_dayYamaghantaka_matchesThePublishedYamagandamTable(int weekday) {
        int expected0Indexed = DAY_YAMAGHANTAKA_PART_1INDEXED[weekday] - 1;
        assertEquals(expected0Indexed, Upagrahas.calcKalavelaPart(GU, weekday, true),
                "day Yamaghantaka part for weekday " + weekday);
    }

    @ParameterizedTest
    @MethodSource("weekdays")
    void calcKalavelaPart_nightGulika_matchesTheBphsDerivedTable(int weekday) {
        int expected0Indexed = NIGHT_GULIKA_PART_1INDEXED[weekday] - 1;
        assertEquals(expected0Indexed, Upagrahas.calcKalavelaPart(SA, weekday, false),
                "night Gulika part for weekday " + weekday);
    }

    @Test
    void calcKalavelaPart_bphsSundayExamples() {
        // "Gulika is the 7th part of daytime, or the 3rd part of nighttime" - BPHS, for Sunday
        assertEquals(6, Upagrahas.calcKalavelaPart(SA, 0, true), "7th part, 0-indexed = 6");
        assertEquals(2, Upagrahas.calcKalavelaPart(SA, 0, false), "3rd part, 0-indexed = 2");
    }

    @Test
    void calcKalavelaPart_everyGrahaGetsExactlyOnePartPerWeekdayAndDayNight() {
        // the 7 grahas SY..SA must partition the 7 parts with no collision, for every weekday
        // and for both day and night - a collision would mean two upagrahas silently share a
        // graha's part when they should not
        for (int weekday = 0; weekday < 7; weekday++) {
            for (boolean dayBirth : new boolean[]{true, false}) {
                boolean[] seen = new boolean[7];
                for (int graha = SY; graha <= SA; graha++) {
                    int part = Upagrahas.calcKalavelaPart(graha, weekday, dayBirth);
                    assertTrue(part >= 0 && part < 7, "part out of range: " + part);
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

    @Test
    void lucknow1947_allSixKalavelaUpagrahasResolveToDistinctSaneValues() {
        IKundali k = new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1947, 8, 15, 10, 30}, 0f, 10.5),
                GEO_LUCKNOW, LAHIRI_AYANAMSA).completeBuild());

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

        // Gulika (middle of Saturn's part) and Maandi (start of the very same part) must not
        // coincide - if they did, the atMiddleOfPart distinction silently stopped doing anything
        assertTrue(Math.abs(gulika.longitude() - maandi.longitude()) > 1e-9,
                "Gulika and Maandi must differ despite sharing Saturn's part");
    }

    @Test
    void lucknow1947_kalavelaUpagrahasAreIndependentOfCallOrder() {
        // all[] is populated once in the constructor; calling the getters in a different
        // order (or twice) must not recompute or mutate anything
        IKundali k = new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1947, 8, 15, 10, 30}, 0f, 10.5),
                GEO_LUCKNOW, LAHIRI_AYANAMSA).completeBuild());

        double first = k.upagrahas().gulika().longitude();
        double second = k.upagrahas().maandi().longitude();
        double third = k.upagrahas().gulika().longitude();

        assertEquals(first, third, 0.0, "repeated gulika() must return the same value");
        assertTrue(Math.abs(first - second) > 1e-9, "gulika/maandi remain distinct regardless of call order");
    }
}
