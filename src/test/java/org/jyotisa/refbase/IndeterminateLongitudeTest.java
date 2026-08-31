/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.app.Kundali;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweObjects.CH;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * A consumer marks a graha as <b>indeterminable</b> by writing NaN into its longitude, and the
 * report has to survive it.
 *
 * <h2>Where the NaN comes from, and why it is not a caller's mistake</h2>
 * An event with a date but no time cannot pin the Moon's sign at all - it moves through more than
 * half a sign in a day - so an app that has only {@code year, month, day} writes NaN rather than
 * an answer it cannot stand behind. {@code jyotisa-basics} does exactly that, through its
 * {@code EpheManager.fixSweObjectsInSigns}, and {@code gaurabda-suppart} does the same.
 *
 * <h2>The defect this closes</h2>
 * The 2026-08-21 NIL work made every lookup answer NIL for NaN instead of silently answering Aries
 * - which was the right half of the fix. What it left open is that a {@code Nil*} member's
 * {@code lord()} is <b>deliberately null</b>: a non-sign has no lord, and inventing one would trade
 * a visible failure for an invisible wrong answer. {@code Kundali.toString()} then dereferenced it:
 * <pre>
 *   pada.naksatra().lord().code()      // NPE when the pada is NIL
 *   pada.navamsa().lord().code()       // and again
 * </pre>
 * Reported from {@code jyotisa-basics} as
 * {@code NullPointerException ... IGraha.code() on a null object reference}, thrown from the trace
 * logging rather than from anything the feed needs - which is why it took a date-only event to
 * surface at all.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class IndeterminateLongitudeTest extends AbstractTest {

    /** the Chennai fixture with one longitude replaced by NaN, as a date-only event produces */
    private Kundali indeterminateMoon() {
        final ISweObjects objects = new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1962, 2, 4, 8, 30}, 5.5f, 8.5),
                GEO_CHENNAI, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild();

        objects.longitudes()[CH] = Double.NaN;
        return new Kundali(KUNDALI_7_KARAKAS, objects);
    }

    @Test
    void theReportSurvivesAnIndeterminableGraha() {
        final Kundali kundali = indeterminateMoon();
        final String report = assertDoesNotThrow(kundali::toString,
                "a NaN longitude is how a caller says 'this cannot be determined', not a bug to"
                        + " throw on - the NIL lookups answer it, and the report must render it");

        assertTrue(report.contains("CH"), "the Moon's row should still be printed: " + report);
    }

    /**
     * And it must not answer with a <i>wrong</i> sign either, which is what the same input did
     * before NaN reached the lookups - the guard here is that the report says nothing definite
     * about the Moon rather than placing it in Aries.
     */
    @Test
    void anIndeterminableGrahaIsNotReportedInAries() {
        final String report = indeterminateMoon().toString();

        for (final String line : report.split("\n")) {
            if (!line.startsWith("CH ")) continue;
            assertTrue(line.contains("NaN") || line.contains("NIL") || line.contains("?"),
                    "the Moon's row must show that nothing is known, was: " + line);
            return;
        }
    }
}
