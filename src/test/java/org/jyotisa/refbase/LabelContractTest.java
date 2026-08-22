/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.junit.jupiter.api.Test;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.graha.guru.GrahaGuru;
import org.jyotisa.lagna.LagnaJanma;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.rasi.RasiMesha;
import org.jyotisa.tithi.TithiPoornima;
import org.swisseph.api.ISweEnumSequence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link ISweEnumSequence#label()} - the display name, given its own accessor.
 *
 * <h2>What it replaced</h2>
 * The report used to reach the display name through {@link ISweEnumSequence#following()}: a
 * concrete family declares its readable name as a second enum constant for the same value
 * ({@code RasiMesha{R1, MES}}), so "the next one" happened to be it. That made a
 * sequence-navigation operation mean "display name", made the answer depend on how many aliases a
 * family declares, and is the root of the {@code GrahaGuru.GU != GrahaGuru.GURU} identity trap.
 * <p>
 * The strings are unchanged - all 17 golden reports and both Lucknow fixtures are byte-identical
 * across the switch, which is the real proof and is why this class only has to pin the rule.
 */
class LabelContractTest {

    // ============================================================ alias families

    @Test
    void anAliasFamilyLabelsItselfWithItsSecondDeclaration() {
        assertEquals("MES", RasiMesha.R1.label());
        assertEquals("JL", LagnaJanma.L0.label());
        assertEquals("GU", GrahaGuru.G3.label());
    }

    /**
     * The label is a property of the value, not of which alias you happen to hold - which is
     * where it differs from {@code following()}, and the reason it reads the second declaration
     * rather than the next one.
     */
    @Test
    void everyAliasOfOneValueGivesTheSameLabel() {
        for (GrahaGuru alias : GrahaGuru.values()) {
            assertEquals("GU", alias.label(), alias.name() + " must label the same value");
        }

        assertEquals("Ju", GrahaGuru.GURU.following().name(),
                "following() is what it always was - a neighbour, and not stable as a label");
    }

    // ============================================================ registries

    /**
     * A registry's constants are different values, so its neighbour is not its label. This is the
     * case a naive {@code following().name()} would have got wrong.
     */
    @Test
    void aRegistryLabelsItselfWithItsOwnCodeRatherThanItsNeighbour() {
        assertEquals("R7", ERasi.TULA.label());
        assertEquals("VRISHABHA", ERasi.MESHA.following().name(), "the neighbour, for contrast");

        for (ERasi rasi : ERasi.values()) {
            assertEquals(rasi.code(), rasi.label(), rasi.name() + " is a registry member");
        }
    }

    /**
     * {@code TithiPoornima{S15, K15}} looks like an alias family but is not - its two constants
     * are the two fortnights' Poornima and carry different codes and uids. It must label each of
     * them as itself, not both as the second.
     */
    @Test
    void twoConstantsWithDifferentCodesAreNotAliasesOfOneValue() {
        assertEquals("S15", TithiPoornima.S15.label());
        assertEquals("K15", TithiPoornima.K15.label());
    }

    @Test
    void aFamilyOfOneAnswersItsCode() {
        assertEquals("NIL", org.jyotisa.api.rasi.NilRasi.NIL.label());
    }

    // ============================================================ across the whole taxonomy

    /**
     * Every sequence in the workspace answers a non-empty label, and never one belonging to a
     * different value - a registry member's label is its own code, an alias family's is shared.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void everySequenceHasAUsableLabel() {
        final List<Class<?>> all = new ArrayList<>(
                SequenceReference.discover(ERasi.class, "org.jyotisa"));
        all.addAll(SequenceReference.discover(IRasi.class, "org.jyotisa.api"));

        int checked = 0;
        for (Class<?> type : all) {
            for (ISweEnumSequence value : (ISweEnumSequence[]) type.getEnumConstants()) {
                final String label = value.label();

                assertNotNull(label, type.getName() + '.' + value.name());
                assertTrue(label.length() > 0, type.getName() + '.' + value.name() + " is blank");
                checked++;
            }
        }

        assertTrue(checked > 400, "expected the whole taxonomy, checked " + checked);
    }

    /** the label never comes from a member that is not this value */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void aLabelNeverBelongsToADifferentValue() {
        final List<Class<?>> all = new ArrayList<>(
                SequenceReference.discover(ERasi.class, "org.jyotisa"));

        for (Class<?> type : all) {
            for (ISweEnumSequence value : (ISweEnumSequence[]) type.getEnumConstants()) {
                if (value.label().equals(value.code())) continue; // the plain case

                // otherwise the label is an alias of this very value - same code, same uid
                boolean found = false;
                for (Object other : value.all()) {
                    final ISweEnumSequence alias = (ISweEnumSequence) other;
                    if (!alias.name().equals(value.label())) continue;

                    assertEquals(value.code(), alias.code(), type.getName() + ": label alias code");
                    assertEquals(value.uid(), alias.uid(), type.getName() + ": label alias uid");
                    found = true;
                }

                assertTrue(found, type.getName() + '.' + value.name()
                        + " labels itself " + value.label() + ", which is not one of its own");
            }
        }
    }

    // ============================================================ the report actually uses it

    @Test
    void theReportsDisplayNamesComeFromLabelNow() {
        // the exact strings the golden files carry, reached the new way
        assertSame(ERasi.MESHA.rasi(), RasiMesha.R1);
        assertEquals("MES", ERasi.byUid(1).label());
        assertEquals("MES", ERasi.byLongitude(10.).label());
    }
}
