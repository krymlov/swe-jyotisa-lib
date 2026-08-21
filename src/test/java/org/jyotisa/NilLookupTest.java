/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaEnum;
import org.jyotisa.karana.EKarana;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.naksatra.ENaksatraPada;
import org.jyotisa.nityayoga.ENityaYoga;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.tithi.ETithi;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.swisseph.api.ISweEnum.NIL_CD;
import static org.swisseph.api.ISweEnum.NIL_FID;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * Every lookup that cannot name a member answers with that family's <b>NIL</b> - never with
 * {@code null}, and never with the first real member.
 * <p>
 * Both wrong answers used to happen, and the second was the dangerous one:
 * <pre>
 * ERasi.byIndex(0)               -&gt; null    NPE at the call site
 * ERasi.byLongitude(NaN)         -&gt; Mesha   a plausible, wrong answer
 * VargaD1.rasi(NaN)              -&gt; Mesha   the same, in every varga
 * ENaksatraPada.byLongitude(NaN) -&gt; Ashwini pada 1
 * </pre>
 * The root cause was two separate things. {@code (int) NaN} is {@code 0} in Java, so
 * {@code byLongitude} indexed the first slot; and {@link org.swisseph.utils.IModuloUtils#modulo}
 * turned NaN into {@code 0.0} outright - its closing {@code rem < mod ? rem : d0} takes the else
 * branch for NaN, because every comparison against NaN is false - so the NaN never even reached
 * the lookups that go through a varga.
 * <p>
 * This matters outside the library: a consumer marks a graha as indeterminable by writing NaN
 * into its longitude (`gaurabda-suppart`'s {@code EpheManager.fixSweObjectsInSigns} does exactly
 * that for a date with no time, because the Moon's sign genuinely cannot be pinned without one),
 * and the chart then reported Aries.
 */
class NilLookupTest {

    private static final double NAN = Double.NaN;

    private static void assertNil(ISweEnum value, String what) {
        assertNotNull(value, what + " must be the NIL member, not null");
        assertTrue(value.isNil(), what + " must be NIL, was " + value.code());
        assertEquals(NIL_FID, value.fid(), what + " fid");
        assertEquals(NIL_CD, value.code(), what + " code");
    }

    // ============================================================ the four reported cases

    @Test
    void theFourLookupsTheCallerReportedAllAnswerNil() {
        assertNil(ERasi.byIndex(0), "ERasi.byIndex(0)");
        assertNil(ERasi.byLongitude(NAN), "ERasi.byLongitude(NaN)");
        assertNil(org.jyotisa.varga.VargaD1.D1.rasi(NAN), "VargaD1.rasi(NaN)");
        assertNil(ENaksatraPada.byLongitude(NAN), "ENaksatraPada.byLongitude(NaN)");
    }

    // ============================================================ every family, every varga

    @Test
    void everyFamilyLookupAnswersNilForNaN() {
        assertNil(ERasi.byLongitude(NAN), "ERasi.byLongitude");
        assertNil(ENaksatra.byLongitude(NAN), "ENaksatra.byLongitude");
        assertNil(ENaksatraPada.byLongitude(NAN), "ENaksatraPada.byLongitude");
        assertNil(ETithi.byOffset(NAN), "ETithi.byOffset");
        assertNil(ETithi.byLongitude(NAN, NAN), "ETithi.byLongitude");
        assertNil(EKarana.byOffset(NAN), "EKarana.byOffset");
        assertNil(ENityaYoga.byOffset(NAN), "ENityaYoga.byOffset");
    }

    static List<IVarga> allVargas() {
        final List<IVarga> vargas = new ArrayList<>(23);
        final ISweEnumIterator<IVargaEnum> iterator = EVarga.iterator();
        while (iterator.hasNext()) vargas.add(iterator.next().varga());
        return vargas;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allVargas")
    void everyVargaAnswersNilForNaN(IVarga varga) {
        assertNil(varga.rasi(NAN), varga.code() + ".rasi(NaN)");
    }

    // ============================================================ index 0 across the registries

    @Test
    void indexAndUidZeroAnswerNilRatherThanNull() {
        assertNil(ERasi.byIndex(0), "ERasi.byIndex(0)");
        assertNil(ERasi.byUid(0), "ERasi.byUid(0)");
        assertNil(ENaksatra.byUid(0), "ENaksatra.byUid(0)");
        assertNil(ETithi.byUid(0), "ETithi.byUid(0)");
        assertNil(EKarana.byUid(0), "EKarana.byUid(0)");
        assertNil(ENityaYoga.byUid(0), "ENityaYoga.byUid(0)");
    }

    // ============================================================ the root cause itself

    @Test
    void moduloPreservesNaNInsteadOfCollapsingItToZero() {
        // this is what let NaN reach the lookups as a perfectly in-range 0.0
        assertTrue(Double.isNaN(fix360(NAN)), "fix360(NaN) must stay NaN");
        assertTrue(Double.isNaN(org.swisseph.utils.IModuloUtils.modulo(30., NAN)),
                "modulo(30, NaN) must stay NaN");

        // and ordinary values are untouched
        assertEquals(10., fix360(370.), 1e-12);
        assertEquals(0., fix360(360.), 1e-12);
        assertEquals(350., fix360(-10.), 1e-12);
    }

    @Test
    void aNilMemberHasNoLordAndAZeroSegment() {
        // a non-sign has no lord and no element - inventing one would trade a visible failure
        // for an invisible wrong answer, which is the whole point of this member
        assertNull(ERasi.byLongitude(NAN).lord(), "NIL rasi has no lord");
        assertNull(ERasi.byLongitude(NAN).tattva(), "NIL rasi has no tattva");
        assertEquals(0., ERasi.byLongitude(NAN).segment().start(), 1e-12);
        assertEquals(0., ERasi.byLongitude(NAN).segment().close(), 1e-12);
    }

    @Test
    void realValuesAreUnaffected() {
        // code() is the technical key (R1..R12); MES/VRB/... are display names reached
        // through following(), which is a separate concern - see the API review reports
        assertEquals("R1", ERasi.byLongitude(0.).code());
        assertEquals("R1", ERasi.byLongitude(29.99).code());
        assertEquals("R2", ERasi.byLongitude(30.).code());
        assertEquals("R12", ERasi.byLongitude(359.99).code());
        assertFalse(ERasi.byLongitude(0.).isNil());
        assertFalse(ENaksatraPada.byLongitude(0.).isNil());
    }
}
