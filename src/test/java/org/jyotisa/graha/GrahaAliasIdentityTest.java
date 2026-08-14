/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.graha;

import org.jyotisa.graha.guru.GrahaGuru;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Every {@code GrahaXxx} enum declares 5 constants for the same graha under different names
 * - e.g. {@code GrahaGuru}: {@code G3, GU, GURU, Ju, Jupiter} (declaration id, 2-letter code,
 * Sanskrit name, astro abbreviation, English name). They are five DISTINCT Java objects
 * (different {@code ordinal()}, different {@code name()}), not aliases of one instance, and
 * neither {@code equals()} nor {@code hashCode()} is overridden anywhere in this family - so
 * {@code fid()}/{@code uid()}/{@code code()} agree across all five (safe to compare), but
 * reference identity (==, {@code assertSame}) does NOT.
 * <p>
 * This matters because the codebase itself is inconsistent about which alias it reaches for:
 * {@code EGraha} (the canonical chart-facing registry) uniformly uses the 2-letter-code form
 * (e.g. {@code GrahaGuru.GU}), while every {@code RasiXxx}/{@code NaksatraXxx}/{@code VaaraXxx}
 * {@code .lord()} method uses the Sanskrit-name form (e.g. {@code GrahaGuru.GURU}) instead.
 * Today nothing in the main source compares these by reference (every call site reads
 * {@code .code()} off the result - see the grep note in this project's CLAUDE.md), so the
 * inconsistency is currently harmless, but it is a live trap for future code that does
 * {@code someGraha == rasi.lord()} or {@code assertSame(...)} across the two conventions.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class GrahaAliasIdentityTest {

    @Test
    void allFiveAliases_agreeOnFidUidCode() {
        GrahaGuru[] aliases = {GrahaGuru.G3, GrahaGuru.GU, GrahaGuru.GURU, GrahaGuru.Ju, GrahaGuru.Jupiter};
        for (GrahaGuru alias : aliases) {
            assertEquals(3, alias.fid(), alias.name());
            assertEquals(GrahaGuru.GU.uid(), alias.uid(), alias.name());
            assertEquals("GU", alias.code(), alias.name());
        }
    }

    @Test
    void theShortCodeAndSanskritNameAliases_areNotTheSameObject() {
        // EGraha.GURU.graha() returns GrahaGuru.GU; RasiDhanus.lord() returns GrahaGuru.GURU.
        // Both are correct "Jupiter" by fid/uid/code, but they fail assertSame/== against
        // each other - this is the exact situation a future reference-equality check would
        // trip on.
        assertNotSame(GrahaGuru.GU, GrahaGuru.GURU);
        assertEquals(GrahaGuru.GU.code(), GrahaGuru.GURU.code(), "code is consistent even though identity is not");
    }
}
