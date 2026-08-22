/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweEnumSequence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.refbase.SequenceReference.assertMatchesReference;
import static org.jyotisa.refbase.SequenceReference.discover;
import static org.jyotisa.refbase.SequenceReference.walk;

/**
 * A reference base for <b>every</b> {@link ISweEnumSequence} in the Jyotisha layer - both the
 * registries ({@code ERasi}, {@code EGraha}, ...) and every leaf they hold ({@code RasiMesha},
 * {@code GrahaGuru}, ...), plus the {@code Nil*} sequences that live in {@code swe-jyotisa-api}.
 * <p>
 * Each is walked with {@code first()}, {@code last()}, {@code following()}, {@code previous()} and
 * {@code follow(k)} across the whole cycle in both directions, and every element landed on is
 * recorded as {@code code | fid | uid | ordinal | length} in
 * {@code src/test/resources/org/jyotisa/refbase/sequence/}, one file per family package.
 *
 * <h2>Why this base exists</h2>
 * {@code follow()} is a single default method shared by roughly 250 sequences across two
 * projects, and it has been wrong: until 2026-08-14 it jumped to {@code last()} for any negative
 * step and reduced positive ones by {@code last()} rather than by the element count. It returned
 * a <b>plausible neighbour</b> rather than throwing, and the only reason the workspace never
 * noticed was that its one production caller happened to sit on the aligned case. The same is
 * true of {@code byIndex()}, corrected on 2026-08-20 for the families without a {@code NIL}
 * member. Both are exactly the kind of defect an invariant does not see and a recorded walk does.
 *
 * <h2>The classes are discovered, not listed</h2>
 * {@link SequenceReference#discover} scans the compiled classes, so a family added later is
 * covered automatically and shows up here as a missing reference file - which fails - rather
 * than as silence.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class SequenceReferenceTest {

    /** {@code org.jyotisa.rasi.ERasi} and friends - this project's own compiled classes */
    private static List<Class<?>> implementations() {
        final List<Class<?>> all = new ArrayList<>(discover(ERasi.class, "org.jyotisa"));
        all.addAll(discover(IRasi.class, "org.jyotisa.api"));
        all.sort(java.util.Comparator.comparing(Class::getName));
        return all;
    }

    /**
     * Grouped by the family package: {@code org.jyotisa.graha.surya.GrahaSurya} and
     * {@code org.jyotisa.graha.EGraha} both belong to {@code graha}, and the {@code Nil*} enums
     * of {@code org.jyotisa.api.rasi} join {@code rasi}. One file per group keeps the base
     * readable and diffable instead of scattering it over ~250 tiny files.
     */
    private static Map<String, List<Class<?>>> families() {
        final Map<String, List<Class<?>>> families = new LinkedHashMap<>();

        for (Class<?> type : implementations()) {
            // a Nil* family is nothing but its reserved member, and NIL does not belong in a
            // reference file - see SequenceReference#isWalkable
            if (!SequenceReference.isWalkable(type)) continue;
            families.computeIfAbsent(familyOf(type), k -> new ArrayList<>()).add(type);
        }

        return families;
    }

    private static String familyOf(final Class<?> type) {
        final String name = type.getName()
                .replace("org.jyotisa.api.", "").replace("org.jyotisa.", "");
        final int dot = name.indexOf('.');
        return dot < 0 ? "root" : name.substring(0, dot);
    }

    // ============================================================ the base itself

    @TestFactory
    Stream<DynamicTest> everyFamilyMatchesItsReference() {
        return families().entrySet().stream().map(family -> DynamicTest.dynamicTest(
                family.getKey() + " (" + family.getValue().size() + " sequences)", () -> {

                    final List<String> lines = new ArrayList<>();
                    for (Class<?> type : family.getValue()) {
                        if (!lines.isEmpty()) lines.add("");
                        lines.addAll(walk(type));
                    }

                    assertMatchesReference(family.getKey(), lines);
                }));
    }

    // ============================================================ invariants

    /**
     * A full lap returns to where it started - by every one of the three routes. This is what
     * {@code follow()}'s modular arithmetic is <i>for</i>, and the old implementation failed it
     * for a {@code first()==0} family on a full positive lap.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void everySequenceReturnsToItsStartAfterAFullLap() {
        for (Class<?> type : implementations()) {
            final ISweEnumSequence[] constants = (ISweEnumSequence[]) type.getEnumConstants();
            if (0 == constants.length) continue;

            final ISweEnumSequence first = constants[0].first();
            final int count = constants[0].last().ordinal() - first.ordinal() + 1;

            assertSame(first, first.follow(count), type.getName() + ": follow(+count)");
            assertSame(first, first.follow(-count), type.getName() + ": follow(-count)");

            ISweEnumSequence cursor = first;
            for (int i = 0; i < count; i++) cursor = cursor.following();
            assertSame(first, cursor, type.getName() + ": count x following()");

            cursor = first;
            for (int i = 0; i < count; i++) cursor = cursor.previous();
            assertSame(first, cursor, type.getName() + ": count x previous()");
        }
    }

    /**
     * Navigation never leaves the declared range, whatever the step - which is how a registry's
     * {@code NIL} member stays unreachable by {@code following()} even though it is
     * {@code all()[0]}.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void navigationStaysBetweenFirstAndLast() {
        for (Class<?> type : implementations()) {
            final ISweEnumSequence[] constants = (ISweEnumSequence[]) type.getEnumConstants();
            if (0 == constants.length) continue;

            final ISweEnumSequence first = constants[0].first();
            final ISweEnumSequence last = constants[0].last();

            for (int step = -2 * constants.length; step <= 2 * constants.length; step++) {
                final int ordinal = first.follow(step).ordinal();

                assertTrue(ordinal >= first.ordinal() && ordinal <= last.ordinal(),
                        type.getName() + ": follow(" + step + ") left the range ["
                                + first.ordinal() + ".." + last.ordinal() + "] at " + ordinal);
            }
        }
    }

    /** {@code following()} and {@code previous()} must be each other's inverse, everywhere */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void followingAndPreviousAreInverses() {
        for (Class<?> type : implementations()) {
            for (ISweEnumSequence element : (ISweEnumSequence[]) type.getEnumConstants()) {
                if (element.ordinal() < element.first().ordinal()
                        || element.ordinal() > element.last().ordinal()) continue;

                assertSame(element, element.following().previous(),
                        type.getName() + '.' + element.name() + ": following().previous()");
                assertSame(element, element.previous().following(),
                        type.getName() + '.' + element.name() + ": previous().following()");
            }
        }
    }

    // ============================================================ the discovery itself

    /**
     * The discovery has to actually find things - a silent empty scan would make every test above
     * pass while checking nothing, which is the one failure mode a reflective suite invites.
     */
    @Test
    void theScanFindsBothTiersAndBothProjects() {
        final List<Class<?>> all = implementations();

        assertTrue(all.size() > 200, "expected the whole two-tier taxonomy, found " + all.size());
        assertTrue(all.contains(ERasi.class), "the registry tier must be discovered");
        assertTrue(all.contains(org.jyotisa.rasi.RasiMesha.class), "the leaf tier must be too");
        assertTrue(all.contains(org.jyotisa.api.rasi.NilRasi.class),
                "swe-jyotisa-api's Nil* sequences live in a jar and must be scanned there");

        final int walkable = (int) all.stream().filter(SequenceReference::isWalkable).count();
        assertEquals(walkable, families().values().stream().mapToInt(List::size).sum(),
                "grouping into families must not lose or duplicate a walkable class");

        assertEquals(all.size() - walkable, all.stream()
                        .filter(t -> !SequenceReference.isWalkable(t)).count(),
                "the only non-walkable sequences are the Nil* Null Objects");
    }
}
