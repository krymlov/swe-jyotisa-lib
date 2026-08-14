/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refcharts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Drives the Swiss Ephemeris reference program from {@code e:\Github\swisseph} and parses
 * its output, so this library can be diffed against it live rather than against numbers
 * pasted into a test.
 * <p>
 * The ephemeris directory deliberately defaults to <b>{@code swe-java-lib}'s</b>
 * {@code ephe}, not this project's: this project ships only the {@code _18} block (year 1800
 * onwards), while the reference charts reach back to year 0. Both the library under test and
 * swetest are pointed at the same directory so neither can silently fall back to Moshier.
 * Override with {@code -Dswetest.exe=...} / {@code -Dswetest.ephe=...}.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public final class Swetest {

    public static final String EXE = System.getProperty("swetest.exe",
            "E:/Github/swisseph/windows/programs/swetest64.exe");

    public static final File EPHE = new File(System.getProperty("swetest.ephe",
            "../swe-java-lib/ephe")).getAbsoluteFile();

    /** swetest body letters in order, and where each lands in {@code ISweObjects} */
    public static final String BODIES = "0123456789";
    public static final String[] BODY_NAMES = {"Sun", "Moon", "Mercury", "Venus", "Mars",
            "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    private Swetest() {
    }

    public static boolean available() {
        return new File(EXE).isFile() && EPHE.isDirectory()
                && new File(EPHE, "sepl_18.se1").isFile();
    }

    /**
     * @param date  {year, month, day} - swetest picks the Julian calendar before 1582-10-15,
     *              exactly as {@code ISweJulianDate.gregorianCalendar} does, so neither side
     *              is told which calendar to use and both deduce the same one
     */
    public static List<String> lines(int[] date, String utcTime, String... extra) {
        final List<String> cmd = new ArrayList<>();
        cmd.add(EXE);
        cmd.add("-b" + date[2] + "." + date[1] + "." + date[0]);
        cmd.add("-ut" + utcTime);
        cmd.add("-eswe");
        cmd.add("-edir" + EPHE.getPath());
        cmd.addAll(Arrays.asList(extra));

        try {
            final Process process = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            final List<String> out = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                for (String line = reader.readLine(); null != line; line = reader.readLine()) {
                    out.add(line);
                }
            }
            process.waitFor();
            return out;
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("cannot run " + String.join(" ", cmd), e);
        }
    }

    /**
     * Every "&lt;name&gt; &lt;number&gt;" line keyed by name. The format string must select a
     * single numeric column (e.g. {@code -fPl}), otherwise the name would swallow all but
     * the last column.
     * <p>
     * A missing ephemeris file only makes swetest print a warning and silently continue with
     * Moshier, which would invalidate every comparison, so that is turned into a failure.
     */
    public static Map<String, Double> values(int[] date, String utcTime, String... extra) {
        final List<String> raw = lines(date, utcTime, extra);
        final Map<String, Double> out = new LinkedHashMap<>();

        for (String line : raw) {
            assertFalse(line.contains("not found") || line.contains("Moshier"),
                    "swetest did not use the Swiss Ephemeris files: " + line + "\n  " + raw);

            final int sp = line.lastIndexOf(' ');
            if (sp <= 0) continue;
            try {
                out.put(line.substring(0, sp).trim(), Double.parseDouble(line.substring(sp + 1).trim()));
            } catch (NumberFormatException notANumericLine) {
                // header or text line
            }
        }
        return out;
    }

    /** "23°31'44.7692" as printed by swetest */
    public static double parseDms(String dms) {
        final String[] parts = dms.split("[°'\"]");
        double value = Math.abs(Double.parseDouble(parts[0].trim()));
        if (parts.length > 1) value += Double.parseDouble(parts[1].trim()) / 60.;
        if (parts.length > 2) value += Double.parseDouble(parts[2].trim()) / 3600.;
        return dms.trim().startsWith("-") ? -value : value;
    }

    /**
     * The UT julian day swetest prints on its {@code "UT:  2451639.014351852 ..."} line -
     * an independent source for anything keyed on the julian day, since
     * {@code new SweJulianDate(date, tz, localTime)} leaves {@code julianDay()} at 0 until
     * an {@code ISweObjects} initialises it.
     */
    public static double julianDayUT(int[] date, String utcTime) {
        for (String line : lines(date, utcTime, "-p0", "-fPl")) {
            final String trimmed = line.trim();
            if (!trimmed.startsWith("UT:")) continue;
            final String rest = trimmed.substring(3).trim();
            final int sp = rest.indexOf(' ');
            return Double.parseDouble(sp < 0 ? rest : rest.substring(0, sp));
        }
        throw new AssertionError("swetest printed no UT julian day");
    }

    /** the ayanamsa swetest prints after "ayanamsa =" for the given sidereal mode */
    public static double ayanamsa(int[] date, String utcTime, int sid) {
        for (String line : lines(date, utcTime, "-p0", "-true", "-sid" + sid, "-fPl")) {
            final int at = line.indexOf("ayanamsa =");
            if (at < 0) continue;
            return parseDms(line.substring(at + 10, line.indexOf('(', at)));
        }
        throw new AssertionError("swetest printed no ayanamsa for sid" + sid);
    }

    public static String house(double geolon, double geolat, char hsys) {
        return "-house" + geolon + "," + geolat + "," + hsys;
    }
}
