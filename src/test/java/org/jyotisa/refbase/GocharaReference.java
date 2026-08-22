/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.swisseph.ISwissEph;
import org.swisseph.api.ISweEnumEntity;
import org.swisseph.api.ISweJulianDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static org.swisseph.utils.IDegreeUtils.toDMSms;

/**
 * Writes and checks the reference files under {@code src/test/resources/org/jyotisa/refbase} -
 * one per iterator configuration, each a fixed number of records of
 *
 * <pre>
 * CODE | 95&deg;42'57.23" | 2025-03-27 05:40:57.89
 * </pre>
 *
 * the entity's code, the longitude it is reached at, and the moment in <b>UTC</b>.
 * <p>
 * <b>These are golden masters, not self-confirming fixtures.</b> The file is read and compared;
 * it is never rewritten by a passing run. On a mismatch the actual output is written to the OS
 * temp directory under the same relative path, so an intended change can be diffed and copied
 * back in one step - the convention {@code KundaliReportGoldenTest} established. A regression in
 * the transit search therefore fails the build instead of quietly re-baselining itself.
 * <p>
 * The dash in the date is a plain {@code '-'} rather than the en dash
 * {@code IDateUtils.F4Y_2M_2D} uses, because this file format is a deliverable in its own right
 * and was specified that way.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public final class GocharaReference {

    /** how many records a reference file holds unless a slow graha makes that impossible */
    public static final int RECORDS = 100;

    /**
     * The first and last year this project's {@code ephe/} actually covers - the {@code _00} to
     * {@code _18} blocks, 600 years each.
     * <p>
     * Past either end Swiss Ephemeris does not fail: it <b>falls back to Moshier</b> with only a
     * warning, and the numbers stay plausible while being roughly 0.02" out. A reference file
     * built across that boundary would silently pin two different engines' output, which is
     * exactly the hazard the workspace notes call out for {@code swetest}. So it is refused here
     * rather than recorded - the file has to be shortened instead.
     */
    public static final int EPHE_FIRST_YEAR = 1, EPHE_LAST_YEAR = 2399;

    private GocharaReference() {
    }

    // ------------------------------------------------------------------ formatting

    /**
     * One record. The moment is rendered from the julian day through {@code ISwissEph}: a
     * {@code SweJulianDate} built from a bare julian day carries no date fields until the engine
     * fills them in, and its {@code date()} answers {@code null} until then.
     * <p>
     * The time zone of such a date is 0, so its local time <i>is</i> UT - which is what the
     * column is meant to hold.
     * <p>
     * {@code Locale.ROOT} is not decoration: the default locale here renders a decimal comma, so
     * without it the seconds column reads {@code 02,23} and the file becomes machine-dependent.
     */
    public static String line(final ISwissEph swissEph, final String code,
                              final double longitude, final double julianDayUT) {
        final ISweJulianDate date = swissEph.getJulianDate(julianDayUT);
        final int[] ymd = date.date();

        if (ymd[0] < EPHE_FIRST_YEAR || ymd[0] > EPHE_LAST_YEAR) {
            throw new IllegalStateException("year " + ymd[0] + " is outside the ephemeris this"
                    + " project ships (" + EPHE_FIRST_YEAR + ".." + EPHE_LAST_YEAR + "), where"
                    + " Swiss Ephemeris silently falls back to Moshier - use fewer records");
        }

        return code + " | " + toDMSms(longitude) + " | "
                + String.format(Locale.ROOT, "%04d-%02d-%02d %02d:%02d:%05.2f",
                ymd[0], ymd[1], ymd[2], date.hours(), date.minutes(), date.dseconds());
    }

    // ------------------------------------------------------------------ collecting

    /**
     * Pulls {@link #RECORDS} entities out of the iterator and renders them.
     *
     * @param codeOf what to put in the first column - the entity's own code for a simple
     *            family, or something composed for one that carries a graha as well
     */
    public static <E extends ISweEnumEntity<?>> List<String> collect(
            final ISwissEph swissEph, final Iterator<E> iterator, final Function<E, String> codeOf) {
        return collect(swissEph, iterator, codeOf, RECORDS);
    }

    /**
     * @param records how many to take - {@link #RECORDS} except where a slow graha would carry
     *            the file past {@link #EPHE_LAST_YEAR}
     */
    public static <E extends ISweEnumEntity<?>> List<String> collect(
            final ISwissEph swissEph, final Iterator<E> iterator,
            final Function<E, String> codeOf, final int records) {

        final List<String> lines = new ArrayList<>(records);

        for (int i = 0; i < records; i++) {
            if (!iterator.hasNext()) {
                throw new IllegalStateException("the iterator ran out after " + i
                        + " of " + records + " records - the reference cannot be built");
            }

            final E entity = iterator.next();
            lines.add(line(swissEph, codeOf.apply(entity), entity.longitude(), entity.julianDay()));
        }

        return lines;
    }

    // ------------------------------------------------------------------ checking

    /**
     * Compares against the checked-in reference - see {@link ReferenceFile}.
     *
     * @param name file name without the extension, e.g. {@code rasi-graha-CH-forward}
     */
    public static void assertMatchesReference(final String name, final List<String> actual)
            throws IOException {
        ReferenceFile.assertMatches(name, actual);
    }
}
