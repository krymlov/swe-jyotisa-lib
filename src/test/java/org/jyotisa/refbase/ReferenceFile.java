/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The golden-master mechanism shared by every reference base under
 * {@code src/test/resources/org/jyotisa/refbase}.
 * <p>
 * <b>These are golden masters, not self-confirming fixtures.</b> The file is read and compared; it
 * is never rewritten by a passing run. On a mismatch the actual output is written to the OS temp
 * directory under the same relative path, so an intended change is a diff and a copy - the
 * convention {@code KundaliReportGoldenTest} established. A regression therefore fails the build
 * instead of quietly re-baselining itself.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public final class ReferenceFile {

    /** where the files live, relative to the test classpath root */
    public static final String RESOURCES = "org/jyotisa/refbase/";

    /**
     * Bootstrap switch: {@code mvn -o test -Dtest=<the test> -Drefbase.generate=true} writes every
     * file without comparing, so a whole base can be created in one run.
     * <p>
     * It is <b>not</b> a "fix the tests" button. It writes to the temp directory like any other
     * run - putting the result into {@code src/test/resources} is a deliberate, reviewable copy,
     * which is what keeps these files golden masters rather than a record of whatever the code
     * did last.
     */
    public static final String GENERATE = "refbase.generate";

    private ReferenceFile() {
    }

    /**
     * @param path resource path relative to {@link #RESOURCES}, without the extension - a plain
     *            name like {@code rasi-graha-CH-forward}, or {@code sequence/rasi} to group a
     *            base in its own folder
     */
    public static void assertMatches(final String path, final List<String> actual)
            throws IOException {

        final String resource = RESOURCES + path + ".txt";
        final String text = String.join("\n", actual);

        save(resource, text);
        if (Boolean.getBoolean(GENERATE)) return;

        assertEquals(load(resource), text, path
                + " differs from its reference; the actual output was written to the temp"
                + " directory as " + resource);
    }

    private static String load(final String resource) throws IOException {
        final URL url = ReferenceFile.class.getClassLoader().getResource(resource);
        assertNotNull(url, "missing reference file: " + resource
                + " - generate it by copying the one written to the temp directory");
        return IOUtils.toString(url, UTF_8).replace("\r\n", "\n").trim();
    }

    private static void save(final String resource, final String text) throws IOException {
        FileUtils.write(new File(FileUtils.getTempDirectory(), resource), text, UTF_8);
    }
}
