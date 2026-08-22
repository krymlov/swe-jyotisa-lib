/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.app.Kundali;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweObjects.LG;
import static org.swisseph.api.ISweObjects.MA;
import static org.swisseph.api.ISweObjects.NOT_CALCULATED;
import static org.swisseph.api.ISweObjects.SY;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * A chart may legitimately be built without every point - {@code buildAscendant = false} is a
 * supported constructor argument, and the partial {@code buildXxx()} builders exist for exactly
 * that. What was missing is a <b>contract</b> for what an unbuilt point reads as.
 *
 * <h2>The defect this closes</h2>
 * {@code signs()} and {@code houses()} are 1-based, so an unbuilt object reads 0. That was never
 * named, and three call sites consumed it as a real sign:
 * <ul>
 * <li>{@code Ashtakavarga} indexed {@code rekha[...][signs[LG] - 1]} - the <b>loud</b> version,
 *     fixed on 2026-08-19 with an {@code ArrayIndexOutOfBoundsException} to show for it;</li>
 * <li>{@code UpagrahaEntity} and the special-lagna rows of {@code Kundali.toString()} compute
 *     {@code (sign + 12 - lagnaSign) % 12 + 1} - the <b>silent</b> version, which with
 *     {@code lagnaSign == 0} answers "sign + 1" and prints it as an ordinary bhava.</li>
 * </ul>
 * Measured before the fix on a chart built with no ascendant: Dhuma reported {@code B2} and
 * Gulika {@code B8}. Both are arithmetic on a lagna that does not exist.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see ISweObjects#isCalculated(int)
 */
class UncalculatedContractTest extends AbstractTest {

    /** 2025-01-01 00:00 UT */
    private static final double Y2025 = 2460676.5;

    /** the same shape {@code AshtakavargaTest} uses: everything but the ascendant */
    private ISweObjects withoutLagna() {
        final ISweObjects objects = new SweObjects(getSwephExp(), new SweJulianDate(Y2025),
                GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE, false);

        objects.buildSunMoon();
        objects.buildMarsKetu();
        objects.buildJupiterSaturn();

        assertEquals(NOT_CALCULATED, objects.signs()[LG], "this fixture must have no ascendant");
        return objects;
    }

    private ISweObjects complete() {
        return new SweObjects(getSwephExp(), new SweJulianDate(Y2025),
                GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild();
    }

    // ============================================================ the contract itself

    @Test
    void isCalculatedDistinguishesABuiltPointFromAnUnbuiltOne() {
        final ISweObjects partial = withoutLagna();

        assertFalse(partial.isCalculated(LG), "the ascendant was deliberately not built");
        assertTrue(partial.isCalculated(SY), "the Sun was");

        final ISweObjects full = complete();
        for (int id = LG; id <= ISweObjects.PL; id++) {
            assertTrue(full.isCalculated(id), "object " + id + " on a complete chart");
        }
    }

    @Test
    void anOutOfRangeIdIsNotCalculatedRatherThanAnIndexError() {
        final ISweObjects objects = complete();

        assertFalse(objects.isCalculated(-1));
        assertFalse(objects.isCalculated(ISweObjects.PL + 1));
        assertFalse(objects.isCalculated(Integer.MAX_VALUE));
    }

    // ============================================================ the silent consumers

    /**
     * The case that was wrong: every upagraha reported a real-looking bhava on a chart with no
     * ascendant. It must say NIL instead.
     */
    @Test
    void upagrahasReportNoBhavaWithoutAnAscendant() {
        final IKundali kundali = new Kundali(KUNDALI_7_KARAKAS, withoutLagna());

        int checked = 0;
        for (IUpagrahaEntity upagraha : kundali.upagrahas().all()) {
            if (null == upagraha) continue;

            assertTrue(upagraha.bhava().isNil(), upagraha.entityEnum().code()
                    + " reported bhava " + upagraha.bhava().code()
                    + " on a chart that has no ascendant to count from");
            checked++;
        }

        assertTrue(checked > 0, "the fixture must actually produce upagrahas");
    }

    @Test
    void upagrahasStillReportARealBhavaWhenThereIsAnAscendant() {
        final IKundali kundali = new Kundali(KUNDALI_7_KARAKAS, complete());

        for (IUpagrahaEntity upagraha : kundali.upagrahas().all()) {
            if (null == upagraha) continue;

            assertFalse(upagraha.bhava().isNil(), upagraha.entityEnum().code()
                    + " must have a bhava on a complete chart");
        }
    }

    /**
     * The whole report renders, and its bhava columns say NIL rather than inventing a number.
     * This is the path {@code Ashtakavarga} used to bring down entirely.
     */
    @Test
    void theReportRendersWithoutAnAscendantAndClaimsNoBhava() {
        final String report = new Kundali(KUNDALI_7_KARAKAS, withoutLagna()).toString();

        assertTrue(report.length() > 1000, "the report must still render");
        assertFalse(report.contains("Bhava= B1 "), "no bhava may be claimed: " + firstBhavaLine(report));
    }

    private static String firstBhavaLine(final String report) {
        for (String line : report.split("\n")) if (line.contains("Bhava=")) return line.trim();
        return "(no bhava column found)";
    }

    // ============================================================ an unbuilt graha

    /**
     * {@code GrahaEntity} reads {@code houses()[uid]} straight into {@code EBhava.byUid(...)}.
     * For an unbuilt graha that is {@code byUid(0)}, which answers the registry's NIL - and
     * before the {@code Nil*} classes existed its {@code bhava()} was {@code null}, so any caller
     * touching it got a {@code NullPointerException}.
     */
    @Test
    void anUnbuiltGrahaAnswersANilBhavaRatherThanNull() {
        final ISweObjects objects = new SweObjects(getSwephExp(), new SweJulianDate(Y2025),
                GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE, false);
        objects.buildSunMoon();

        assertFalse(objects.isCalculated(MA), "Mangala was never built");

        final IKundali kundali = new Kundali(KUNDALI_7_KARAKAS, objects);
        assertTrue(kundali.grahas().all()[MA].bhava().isNil(),
                "an unbuilt graha must answer the Null Object, not null and not a real bhava");
    }
}
