/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.junit.jupiter.api.Test;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.api.ISweEnumSequence;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The contract of the reserved {@link ISweEnum#isNil() NIL} member, stated once and enforced over
 * every sequence in the workspace by reflection rather than family by family.
 *
 * <pre>
 * 1. a failed by... or find... lookup answers NIL - never null, never an exception
 * 2. NIL is reserved: first() never answers it
 * 3. NIL is invisible to navigation: no walk around the cycle ever reaches it
 * 4. NIL is not in the reference files          - SequenceReference#isWalkable
 * 5. all() may return it - that is how a caller reaches it deliberately
 * 6. iterator(), iteratorFrom(), iteratorTo() never yield it
 * </pre>
 *
 * <h2>Where the contract cannot hold, and why that is honest</h2>
 * Two kinds of family have no NIL to answer with, and inventing one for them would be worse than
 * the exception they still throw:
 * <ul>
 * <li><b>{@code EGraha} and {@code ELagna}</b> deliberately declare no reserved member - every
 *     one of their constants is a real body or lagna;</li>
 * <li><b>alias leaves</b> such as {@code GrahaGuru{G3, GU, GURU, Ju, Jupiter}} are five names for
 *     one value, so there is no "not a value" among them.</li>
 * </ul>
 * A {@code Nil*} class is the mirror image - it is <i>only</i> NIL - so rule 2 is relaxed there
 * for the same reason: there is nothing else it could return.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class NilContractTest {

    private static List<Class<?>> sequences() {
        final List<Class<?>> all = new ArrayList<>(
                SequenceReference.discover(ERasi.class, "org.jyotisa"));
        all.addAll(SequenceReference.discover(IRasi.class, "org.jyotisa.api"));
        return all;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ISweEnumSequence[] constantsOf(final Class<?> type) {
        return (ISweEnumSequence[]) type.getEnumConstants();
    }

    /** the registries - the classes whose static {@code by*}/{@code iterator*} factories are API */
    private static List<Class<?>> registries() {
        final List<Class<?>> registries = new ArrayList<>();
        for (Class<?> type : sequences()) {
            if (type.getSimpleName().startsWith("E")) registries.add(type);
        }
        return registries;
    }

    // ============================================================ 2. first() is never NIL

    @Test
    void firstNeverAnswersTheReservedMember() {
        for (Class<?> type : sequences()) {
            if (!SequenceReference.isWalkable(type)) continue; // a Nil* family is only NIL

            final ISweEnumSequence<?> first = constantsOf(type)[0].first();
            assertFalse(first.isNil(), type.getName() + ".first() answered NIL");
        }
    }

    @Test
    void lastNeverAnswersTheReservedMemberEither() {
        for (Class<?> type : sequences()) {
            if (!SequenceReference.isWalkable(type)) continue;

            final ISweEnumSequence<?> last = constantsOf(type)[0].last();
            assertFalse(last.isNil(), type.getName() + ".last() answered NIL");
        }
    }

    /**
     * Every family declares its own {@code first()}/{@code last()}, so the NIL-skipping defaults
     * added on 2026-08-22 are never reached from production code - reverting them to
     * {@code all()[0]} leaves this whole sweep green, which is why they are also tested directly
     * in {@code ISweEnumSequenceNilTest} against a family that does not override them.
     * <p>
     * What <i>this</i> test adds is the other half: the hand-written overrides and the default
     * must not contradict each other. The rule is <b>narrowing is allowed, widening is not</b> -
     * an override may exclude a real member from the cycle, but it may never reach past the
     * default's bounds, because the only thing out there is the reserved member.
     *
     * <h2>Two families do narrow, and both are right to</h2>
     * <ul>
     * <li>{@code SweAyanamsa} keeps {@code AY_NONE} at ordinal 48 of 49 - the tropical
     *     "no ayanamsa" setting, a real configuration that has no place in a cycle of
     *     ayanamsas;</li>
     * <li>{@code EMaasa} keeps {@code PURADH} - Purushottama Adhika Maasa, the intercalary
     *     month - past {@code VISNU}, so a walk covers the twelve regular months and not the
     *     thirteenth that only some years have.</li>
     * </ul>
     * Neither is a NIL, so neither is covered by the reserved-member rules; both are found by
     * this test rather than assumed, which is why it asserts the direction rather than equality.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void anOverriddenBoundMayNarrowTheCycleButNeverReachIntoTheReservedMember() {
        int checked = 0, narrowed = 0;

        for (Class<?> type : sequences()) {
            if (!SequenceReference.isWalkable(type)) continue;
            final ISweEnumSequence[] constants = constantsOf(type);

            // what the default computes, worked out here rather than by calling the override
            int defaultFirst = 0;
            while (constants[defaultFirst].isNil()) defaultFirst++;

            int defaultLast = constants.length - 1;
            while (constants[defaultLast].isNil()) defaultLast--;

            final ISweEnumSequence first = constants[0].first();
            final ISweEnumSequence last = constants[0].last();

            assertTrue(first.ordinal() >= defaultFirst, type.getName() + ".first() is "
                    + first.name() + ", before the first non-reserved member");
            assertTrue(last.ordinal() <= defaultLast, type.getName() + ".last() is "
                    + last.name() + ", past the last non-reserved member");

            if (first.ordinal() != defaultFirst || last.ordinal() != defaultLast) narrowed++;
            checked++;
        }

        assertTrue(checked > 200, "expected the whole taxonomy, checked " + checked);
        assertTrue(narrowed <= 1, "EMaasa is the only jyotisa family that narrows its own cycle;"
                + " a second one appearing here needs a look, found " + narrowed);
    }

    // ============================================================ 3. the cycle never reaches NIL

    /**
     * Two full laps in each direction plus every absolute {@code follow(k)} - so a NIL anywhere
     * in the array, not merely at ordinal 0, would be caught.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void noWalkAroundTheCycleEverReachesTheReservedMember() {
        for (Class<?> type : sequences()) {
            if (!SequenceReference.isWalkable(type)) continue;

            final ISweEnumSequence[] constants = constantsOf(type);
            final ISweEnumSequence first = constants[0].first();
            final int count = constants[0].last().ordinal() - first.ordinal() + 1;

            ISweEnumSequence cursor = first;
            for (int i = 0; i < 2 * count; i++) {
                assertFalse(cursor.isNil(), type.getName() + ": following() reached NIL at step " + i);
                cursor = cursor.following();
            }

            cursor = constants[0].last();
            for (int i = 0; i < 2 * count; i++) {
                assertFalse(cursor.isNil(), type.getName() + ": previous() reached NIL at step " + i);
                cursor = cursor.previous();
            }

            for (int k = -2 * count; k <= 2 * count; k++) {
                assertFalse(first.follow(k).isNil(),
                        type.getName() + ": follow(" + k + ") reached NIL");
            }
        }
    }

    // ============================================================ 6. iterators never yield NIL

    /**
     * Every no-argument {@code iterator()} factory, and - the case that actually needed fixing -
     * {@code iteratorFrom(NIL)}, which hands the iterator the reserved member as its starting
     * point. It must step over it rather than yield it.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void noIteratorFactoryEverYieldsTheReservedMember() throws Exception {
        int checkedFrom = 0;

        for (Class<?> type : registries()) {
            for (Method method : type.getDeclaredMethods()) {
                if (!method.getName().startsWith("iterator")) continue;
                if (!Modifier.isStatic(method.getModifiers())) continue;

                if (0 == method.getParameterCount()) {
                    drain(type.getSimpleName() + "." + method.getName() + "()",
                            (Iterator) method.invoke(null));
                    continue;
                }

                // iteratorFrom(NIL) / iteratorTo(NIL) - start or end on the reserved member
                final ISweEnumSequence nil = ISweEnum.nil(constantsOf(type));
                if (null == nil || 1 != method.getParameterCount()) continue;
                if (!method.getParameterTypes()[0].isInstance(nil)) continue;

                drain(type.getSimpleName() + "." + method.getName() + "(NIL)",
                        (Iterator) method.invoke(null, nil));
                checkedFrom++;
            }
        }

        assertTrue(checkedFrom > 10, "expected the iteratorFrom/iteratorTo(NIL) cases to be"
                + " exercised across the registries, only reached " + checkedFrom);
    }

    private static void drain(final String what, final Iterator<?> iterator) {
        assertNotNull(iterator, what);

        for (int guard = 0; iterator.hasNext() && guard < 1000; guard++) {
            final ISweEnum value = (ISweEnum) iterator.next();
            assertFalse(value.isNil(), what + " yielded the reserved NIL member");
        }
    }

    // ============================================================ 1. lookups answer NIL

    /**
     * A miss on {@code findByName}/{@code findByCode}/{@code findByUid}/{@code findByFid} returns
     * the family's NIL - not null, and without throwing.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void aFailedFindAnswersNilForEveryFamilyThatHasOne() {
        for (Class<?> type : sequences()) {
            final ISweEnumSequence[] constants = constantsOf(type);
            final ISweEnumSequence nil = ISweEnum.nil(constants);
            if (null == nil) continue; // EGraha, ELagna, alias leaves - nothing to answer with

            final ISweEnumSequence any = constants[0];

            assertSame(nil, any.findByName("no such name"), type.getName() + ".findByName");
            assertSame(nil, any.findByCode("no such code"), type.getName() + ".findByCode");
            assertSame(nil, any.findByUid(Integer.MIN_VALUE), type.getName() + ".findByUid");
            assertSame(nil, any.findByFid(Integer.MIN_VALUE), type.getName() + ".findByFid");
            assertSame(nil, any.nil(), type.getName() + ".nil()");
        }
    }

    /** the same for the static lookups every registry's {@code by*} delegates to */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void aFailedStaticLookupAnswersNilRatherThanThrowing() {
        for (Class<?> type : sequences()) {
            final ISweEnumSequence[] constants = constantsOf(type);
            final ISweEnumSequence nil = ISweEnum.nil(constants);
            if (null == nil) continue;

            assertSame(nil, ISweEnum.byFid(Integer.MIN_VALUE, constants), type.getName() + " byFid");
            assertSame(nil, ISweEnum.byUid(Integer.MIN_VALUE, constants), type.getName() + " byUid");
            assertSame(nil, ISweEnum.byCode("no such code", constants), type.getName() + " byCode");
            assertSame(nil, ISweEnum.byName("no such name", constants), type.getName() + " byName");
            assertSame(nil, ISweEnum.byIndex(-1, constants), type.getName() + " byIndex(-1)");
            assertSame(nil, ISweEnum.byCode(null, constants), type.getName() + " byCode(null)");
            assertSame(nil, ISweEnum.byName(null, constants), type.getName() + " byName(null)");
        }
    }

    /**
     * A family with no reserved member still fails loudly - it has nothing truthful to answer
     * with, and quietly returning something would be worse than the exception.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void aFamilyWithoutANilStillThrows() {
        final ISweEnumSequence[] grahas = constantsOf(org.jyotisa.graha.EGraha.class);

        org.junit.jupiter.api.Assertions.assertNull(ISweEnum.nil(grahas),
                "EGraha deliberately declares no NIL member");
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> ISweEnum.byUid(Integer.MIN_VALUE, grahas));
    }

    // ============================================================ 5. all() still returns NIL

    @Test
    void allStillReturnsTheReservedMemberSoItStaysReachableOnPurpose() {
        int found = 0;

        for (Class<?> type : sequences()) {
            final ISweEnumSequence<?>[] constants = constantsOf(type);
            if (null == ISweEnum.nil(constants)) continue;

            boolean inAll = false;
            for (Object value : constants[0].all()) {
                if (((ISweEnum) value).isNil()) inAll = true;
            }

            assertTrue(inAll, type.getName() + ".all() dropped its NIL member -"
                    + " all() is the one route that must still expose it");
            found++;
        }

        assertTrue(found > 15, "expected the NIL-declaring families, found " + found);
    }

    // ============================================================ the registries' own by*

    /**
     * The end-to-end shape a caller actually sees: {@code ERasi.byUid(999)} answers
     * {@code NilRasi.NIL}, not an exception and not null - and its accessors are safe to reach.
     */
    @Test
    void aRegistryLookupMissAnswersTheNullObject() {
        final IRasi missed = ERasi.byUid(Integer.MIN_VALUE);

        assertNotNull(missed, "a miss must answer the Null Object");
        assertTrue(missed.isNil(), "and it must be the NIL one: " + missed.code());

        assertSame(missed, ERasi.byIndex(-1));
        assertSame(missed, ERasi.byName("no such rasi"));
        assertSame(missed, ERasi.byLongitude(Double.NaN));
    }

    /**
     * The end-to-end contract over <b>every</b> registry: each of its static {@code by*} lookups,
     * given something that names nothing, answers a non-null NIL object.
     * <p>
     * This is what the ten {@code Nil*} classes added on 2026-08-22 are for. Before them
     * {@code EBhava.NIL.bhava()} and nine others answered {@code null}, so making the lookups
     * total would merely have moved the {@code null} one call further along - {@code by*} returns
     * the <i>leaf</i>, not the registry member.
     * <p>
     * Driven by reflection over the actual static methods rather than a list, so a registry's new
     * lookup is covered the day it is written.
     */
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void everyRegistryLookupAnswersANullObjectOnAMiss() throws Exception {
        int checked = 0;

        for (Class<?> type : registries()) {
            if (null == ISweEnum.nil(constantsOf(type))) continue; // EGraha, ELagna

            for (Method method : type.getDeclaredMethods()) {
                if (!Modifier.isStatic(method.getModifiers())) continue;
                if (!method.getName().startsWith("by") || 1 != method.getParameterCount()) continue;
                if (!ISweEnum.class.isAssignableFrom(method.getReturnType())) continue;

                final Object miss = missFor(method.getParameterTypes()[0]);
                if (null == miss) continue; // takes a member or a longitude, not a key

                final String what = type.getSimpleName() + '.' + method.getName() + '(' + miss + ')';
                final Object value = method.invoke(null, miss);

                assertNotNull(value, what + " answered null");
                assertTrue(((ISweEnum) value).isNil(), what + " answered "
                        + ((ISweEnum) value).code() + " rather than the NIL object");
                checked++;
            }
        }

        assertTrue(checked >= 30, "expected every NIL-declaring registry's by* lookups to be"
                + " exercised, reached " + checked);
    }

    /** a key of that type which no member can carry, or null if the parameter is not a key */
    private static Object missFor(final Class<?> parameter) {
        if (int.class == parameter || Integer.class == parameter) return Integer.MIN_VALUE;
        if (String.class == parameter) return "no such member";
        return null;
    }
}
