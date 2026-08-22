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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <b>A code names one thing.</b> No two families may use the same {@link ISweEnum#code()}.
 *
 * <h2>Why this is worth a test</h2>
 * A code is the identity a chart is rendered and parsed by - {@code R7}, {@code B12}, {@code D9},
 * {@code AV3}. If a code meant one thing in one family and something else in another, the report
 * would be ambiguous and any {@code byCode} lookup that scans more than one family would answer
 * whichever it happened to reach first. The families are added one at a time and years apart, so
 * nothing but a check keeps them apart.
 * <p>
 * It has real bite: the avastha family wanted the readable {@code MRT}, {@code KUM} and friends,
 * and although those are free as <i>codes</i>, {@code MRT} is already the Mrityu upagraha's name
 * and {@code KUM} Kumbha's label in the very same report. It took {@code AV1}..{@code AV5}
 * instead.
 *
 * <h2>What "the same family" means</h2>
 * A registry, its leaves and their aliases all carry one code on purpose - {@code ERasi.MESHA},
 * {@code RasiMesha.R1} and {@code RasiMesha.MES} are all {@code "R1"}, and that is the point of a
 * code. They live in one package, so the check groups by the family package and only complains
 * when <b>different</b> families collide.
 *
 * <h2>The one code every family shares</h2>
 * {@code NIL}, and it is not an exception to the rule so much as the rule's other half: the
 * reserved member is deliberately the same everywhere, because "not a value" needs no family of
 * its own. {@link NilContractTest} covers what it must and must not do.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class EnumCodeUniquenessTest {

    private static List<Class<?>> enums() {
        final List<Class<?>> all = new ArrayList<>(
                SequenceReference.discover(ERasi.class, "org.jyotisa"));
        all.addAll(SequenceReference.discover(IRasi.class, "org.jyotisa.api"));
        return all;
    }

    /** {@code org.jyotisa.graha.surya.GrahaSurya} and {@code org.jyotisa.api.graha.IGraha} are one */
    private static String familyOf(final Class<?> type) {
        final String name = type.getName()
                .replace("org.jyotisa.api.", "").replace("org.jyotisa.", "");
        final int dot = name.indexOf('.');
        return dot < 0 ? "root" : name.substring(0, dot);
    }

    /** every code in the workspace, and which families use it */
    private static Map<String, Set<String>> codesByFamily() {
        final Map<String, Set<String>> byCode = new TreeMap<>();

        for (Class<?> type : enums()) {
            for (Object constant : type.getEnumConstants()) {
                final String code;
                try {
                    code = ((ISweEnum) constant).code();
                } catch (Throwable unimplemented) {
                    continue;   // EMaasa's members declare a code they cannot compute
                }
                if (null == code) continue;

                byCode.computeIfAbsent(code, key -> new TreeSet<>()).add(familyOf(type));
            }
        }

        return byCode;
    }

    @Test
    void noTwoFamiliesShareACode() {
        final List<String> shared = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : codesByFamily().entrySet()) {
            if (entry.getValue().size() < 2) continue;
            if (ISweEnum.NIL_CD.equals(entry.getKey())) continue;   // the reserved member

            shared.add(entry.getKey() + " is used by " + entry.getValue());
        }

        assertTrue(shared.isEmpty(), shared.size() + " code(s) mean different things in"
                + " different families:\n  " + String.join("\n  ", shared));
    }

    /**
     * And the scan has to be finding things - a check that silently examines nothing passes
     * forever. The count is a floor rather than a fixture so that adding a family does not have
     * to touch this file.
     */
    @Test
    void theScanReachesTheWholeTaxonomy() {
        final Map<String, Set<String>> byCode = codesByFamily();

        assertTrue(byCode.size() > 350, "expected the whole taxonomy, found "
                + byCode.size() + " distinct codes");

        final Set<String> families = new LinkedHashSet<>();
        for (Set<String> using : byCode.values()) families.addAll(using);

        assertTrue(families.size() > 15, "expected every family, found " + families);
        assertTrue(families.contains("avastha"), "the newest family must be in the scan too");
    }

    /**
     * {@code NIL} is the one code every family that declares a reserved member shares, and that is
     * deliberate. Pinned so the exemption above stays a statement rather than a loophole.
     */
    @Test
    void nilIsTheOnlyCodeSharedOnPurpose() {
        final Set<String> families = codesByFamily().get(ISweEnum.NIL_CD);

        assertTrue(families.size() > 10, "NIL is shared by every family that reserves a member,"
                + " found only " + families);
        assertEquals(0, families.stream().filter(f -> f.isEmpty()).count());
    }
}
