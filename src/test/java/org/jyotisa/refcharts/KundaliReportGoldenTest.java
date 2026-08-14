/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refcharts;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.jyotisa.app.Kundali;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweObjects;
import org.swisseph.app.SweObjectsOptions;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweAyanamsa.LAHIRI;
import static org.swisseph.app.SweHouseSystem.WHOLE_SIGN;

/**
 * The whole horoscope {@code Kundali.toString()} renders, for each of the seventeen
 * Jagannatha Hora reference epochs, held against a checked-in copy of itself.
 * <p>
 * This complements {@link KundaliRefChartsTest}, which diffs the individual numbers against
 * {@code swetest} but only reaches the columns it knows how to recompute. The columns it
 * cannot - the rasi/naksatra progress percentages, the dignity and chara karaka codes, and
 * the whole {@code KundaliFields} block of julian day, delta t, sidereal time and the
 * rise/set times - are covered here instead. Every number in these files was verified
 * against swetest by that test before being committed, so they are a validated snapshot
 * rather than merely "whatever the code printed".
 * <p>
 * On a mismatch the actual report is written into the OS temp directory under the same
 * relative path, so an intended change can be diffed and copied back in one step - the same
 * convention {@code org.jyotisa.AbstractTest.loadAndAssert} established.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class KundaliReportGoldenTest {

    private ISwissEph swissEph;

    private ISwissEph swissEph() {
        if (null == swissEph) swissEph = new SwephNative(Swetest.EPHE.getPath());
        return swissEph;
    }

    @AfterEach
    void closeSwissEph() {
        if (null != swissEph) {
            swissEph.close();
            swissEph = null;
        }
    }

    @ParameterizedTest(name = "ref{0} report")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void reportMatchesTheCheckedInReference(int year) throws IOException {
        // the ephemeris has to be the same one the reference was captured with, or every
        // longitude in the file shifts
        assumeTrue(Swetest.EPHE.isDirectory() && new File(Swetest.EPHE, "sepl_18.se1").isFile(),
                "ephemeris not available at " + Swetest.EPHE);

        final JhdChart jhd = KundaliRefChartsTest.jhd(year);
        final ISweObjects objects = new SweObjects(swissEph(), jhd.julianDate(), jhd.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(LAHIRI).houseSystem(WHOLE_SIGN).build())
                .completeBuild();

        final String actual = new Kundali(KUNDALI_7_KARAKAS, objects).toString().trim();
        final String resource = String.format("org/jyotisa/refcharts/kundali%04d.txt", year);

        save(resource, actual);   // always, so a diff is one copy away
        assertEquals(load(resource), actual, "report for " + year
                + " differs; the actual one was written next to your temp directory as " + resource);
    }

    private static String load(String resource) throws IOException {
        final URL url = KundaliReportGoldenTest.class.getClassLoader().getResource(resource);
        assertNotNull(url, "missing reference report: " + resource);
        return IOUtils.toString(url, UTF_8).trim();
    }

    private static void save(String resource, String content) throws IOException {
        FileUtils.write(new File(FileUtils.getTempDirectory(), resource), content, UTF_8);
    }
}
