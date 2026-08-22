/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refjhora8;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Reads one of the <b>Jagannatha Hora</b> reference reports in
 * {@code src/test/resources/org/jyotisa/refjhora8}.
 *
 * <h2>What Jagannatha Hora is, and why it is the reference</h2>
 * JHora is P.V.R. Narasimha Rao's Vedic astrology program - the software the
 * {@code ref<year>.jhd} fixtures in {@code org/jyotisa/refcharts} were authored for, and the one
 * this workspace has been checked against since 2026-08-16. It is an independent implementation
 * of the whole Jyotisha layer on its own Swiss Ephemeris build, so where it and {@code Kundali}
 * agree, two unrelated programs agree; where they differ, the difference is a question about the
 * rule rather than about arithmetic.
 *
 * <h2>The file</h2>
 * A Ukrainian-language markdown dump of the full JHora natal report, fenced blocks keyed by
 * heading. Four are read here:
 * <ul>
 * <li><b>Основні дані</b> - ayanamsa, sidereal time, sunrise and sunset in <i>local</i> time, and
 *     the panchanga with the <b>remaining</b> percentage of each element (this library prints the
 *     elapsed one, so the two sum to 100);</li>
 * <li><b>Довготи планет і точок</b> - every object's D-1 longitude to 1/100 arcsecond, with its
 *     naksatra, pada, rasi and navamsa;</li>
 * <li><b>Довготи в усіх варґах</b> - the sixteen classical vargas, each cell rendered as
 *     {@code <deg><Sign><min>} with the minutes <b>rounded and not carried</b>, so {@code 14Vi60}
 *     means 14&deg;60' rather than 15&deg;00';</li>
 * <li><b>Аштакаварґа расі-карти</b> - the eight Bhinnashtakavarga rows.</li>
 * </ul>
 * Ukrainian keys are written as escapes so the file stays pure ASCII and cannot be broken by an
 * encoding mishap; each is followed by the word it spells.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public final class JhoraReport {

    /** JHora's two-letter sign abbreviations, in this library's own three-letter codes */
    static final Map<String, String> SIGNS = new LinkedHashMap<>();

    /** the objects both programs compute, JHora's name to this library's report code */
    static final Map<String, String> OBJECTS = new LinkedHashMap<>();

    /** JHora's graha abbreviation, as it appears in "(Ju)", to this library's code */
    static final Map<String, String> LORDS = new LinkedHashMap<>();

    /** the Bhinnashtakavarga row labels */
    static final Map<String, String> BAV_POINTS = new LinkedHashMap<>();

    /** JHora's chara-karaka suffix, as it appears in "Sun - Bk" */
    static final Map<String, String> KARAKAS = new LinkedHashMap<>();

    static {
        final String[][] signs = {{"Ar", "MES"}, {"Ta", "VRB"}, {"Ge", "MIT"}, {"Cn", "KAR"},
                {"Le", "SIM"}, {"Vi", "KAN"}, {"Li", "TUL"}, {"Sc", "VRC"}, {"Sg", "DHN"},
                {"Cp", "MAK"}, {"Aq", "KUM"}, {"Pi", "MEE"}};
        for (String[] pair : signs) SIGNS.put(pair[0], pair[1]);

        // the thirteen grahas
        OBJECTS.put("\u041b\u0430\u0491\u043d\u0430", "LG");                   // Lagna
        OBJECTS.put("\u0421\u043e\u043d\u0446\u0435", "SY");                   // Surya
        OBJECTS.put("\u041c\u0456\u0441\u044f\u0446\u044c", "CH");             // Chandra
        OBJECTS.put("\u041c\u0430\u0440\u0441", "MA");                         // Mangala
        OBJECTS.put("\u041c\u0435\u0440\u043a\u0443\u0440\u0456\u0439", "BU");  // Budha
        OBJECTS.put("\u042e\u043f\u0456\u0442\u0435\u0440", "GU");             // Guru
        OBJECTS.put("\u0412\u0435\u043d\u0435\u0440\u0430", "SK");             // Shukra
        OBJECTS.put("\u0421\u0430\u0442\u0443\u0440\u043d", "SA");             // Shani
        OBJECTS.put("\u0420\u0430\u0445\u0443", "RA");                         // Rahu
        OBJECTS.put("\u041a\u0435\u0442\u0443", "KE");                         // Ketu
        OBJECTS.put("\u0423\u0440\u0430\u043d", "SW");                         // Uranus / Sweta
        OBJECTS.put("\u041d\u0435\u043f\u0442\u0443\u043d", "SM");             // Neptune / Syama
        OBJECTS.put("\u041f\u043b\u0443\u0442\u043e\u043d", "TE");             // Pluto / Teevra

        // the eleven upagrahas
        OBJECTS.put("\u0414\u0445\u0443\u043c\u0430", "DHU");                  // Dhuma
        OBJECTS.put("\u0412'\u044f\u0442\u0456\u043f\u0430\u0442\u0430", "VYA"); // Vyatipaata
        OBJECTS.put("\u041f\u0430\u0440\u0456\u0432\u0435\u0448\u0430", "PAR"); // Parivesha
        OBJECTS.put("\u0406\u043d\u0434\u0440\u0430 \u0427\u0430\u043f\u0430", "CHP"); // Indrachaapa
        OBJECTS.put("\u0423\u043f\u0430\u043a\u0435\u0442\u0443", "UPK");      // Upaketu
        OBJECTS.put("\u041a\u0430\u0430\u043b\u0430", "KAA");                  // Kaala
        OBJECTS.put("\u041c\u0440\u0456\u0442\u044c\u044e", "MRT");            // Mrityu
        OBJECTS.put("\u0410\u0440\u0442\u0445\u0430 \u041f\u0440\u0430\u0445\u0430\u0440\u0430", "ART"); // Arthaprahaara
        OBJECTS.put("\u042f\u043c\u0430 \u0490\u0445\u0430\u043d\u0442\u0430\u043a\u0430", "YAM");       // Yamaghantaka
        OBJECTS.put("\u0490\u0443\u043b\u0456\u043a\u0430", "GLK");            // Gulika
        OBJECTS.put("\u041c\u0430\u043d\u0434\u0456", "MND");                  // Maandi

        // the eight special lagnas beyond the ascendant
        OBJECTS.put("\u0411\u0445\u0430\u0432\u0430 \u043b\u0430\u0491\u043d\u0430", "BL");      // Bhava lagna
        OBJECTS.put("\u0425\u043e\u0440\u0430 \u043b\u0430\u0491\u043d\u0430", "HL");           // Hora lagna
        OBJECTS.put("\u0490\u0445\u0430\u0442\u0456 \u043b\u0430\u0491\u043d\u0430", "GL");     // Ghati lagna
        OBJECTS.put("\u0412\u0456\u0491\u0445\u0430\u0442\u0456 \u043b\u0430\u0491\u043d\u0430", "VG"); // Vighati lagna
        OBJECTS.put("\u0412\u0430\u0440\u043d\u0430\u0434\u0430 \u043b\u0430\u0491\u043d\u0430", "VL"); // Varnada lagna
        OBJECTS.put("\u0428\u0440\u0456 \u041b\u0430\u0491\u043d\u0430", "SL");                 // Sree lagna
        OBJECTS.put("\u041f\u0440\u0430\u043d\u0430\u043f\u0430\u0434\u0430 \u043b\u0430\u0491\u043d\u0430", "PP"); // Pranapada lagna
        OBJECTS.put("\u0406\u043d\u0434\u0443 \u043b\u0430\u0491\u043d\u0430", "IL");           // Indu lagna

        final String[][] lords = {{"Su", "SY"}, {"Mo", "CH"}, {"Ma", "MA"}, {"Me", "BU"},
                {"Ju", "GU"}, {"Ve", "SK"}, {"Sa", "SA"}, {"Ra", "RA"}, {"Ke", "KE"}};
        for (String[] pair : lords) LORDS.put(pair[0], pair[1]);

        final String[][] bav = {{"As", "LG"}, {"Su", "SY"}, {"Mo", "CH"}, {"Ma", "MA"},
                {"Me", "BU"}, {"Ju", "GU"}, {"Ve", "SK"}, {"Sa", "SA"}};
        for (String[] pair : bav) BAV_POINTS.put(pair[0], pair[1]);

        KARAKAS.put("\u0410\u043a", "AK");         // Ak  - Atmakaraka
        KARAKAS.put("\u0410\u043c\u043a", "AmK");  // Amk - Amatyakaraka
        KARAKAS.put("\u0411\u043a", "BK");         // Bk  - Bhratrikaraka
        KARAKAS.put("\u041c\u043a", "MK");         // Mk  - Matrikaraka
        KARAKAS.put("\u041f\u043a", "PK");         // Pk  - Putrakaraka
        KARAKAS.put("\u0490\u043a", "GK");         // Gk  - Gnatikaraka
        KARAKAS.put("\u0414\u043a", "DK");         // Dk  - Darakaraka
    }

    // ------------------------------------------------------------------ what one row holds

    /** one object of the high-precision D-1 section */
    public static final class Point {
        public final String code;
        /** absolute sidereal longitude in degrees */
        public final double longitude;
        public final String rasi, navamsa, naksatra;
        public final int pada;
        public final boolean retrograde;
        /** the chara karaka in this library's spelling, or {@code null} where JHora prints none */
        public final String karaka;

        Point(String code, double longitude, String rasi, String navamsa,
              String naksatra, int pada, boolean retrograde, String karaka) {
            this.code = code;
            this.longitude = longitude;
            this.rasi = rasi;
            this.navamsa = navamsa;
            this.naksatra = naksatra;
            this.pada = pada;
            this.retrograde = retrograde;
            this.karaka = karaka;
        }
    }

    /**
     * One cell of the varga table, as JHora renders it: a sign plus whole degrees and rounded
     * minutes. There is no finer figure in the file, so a comparison against it can only be as
     * precise as one arcminute.
     */
    public static final class Cell {
        public final String rasi;
        public final int degrees, minutes;

        Cell(String rasi, int degrees, int minutes) {
            this.rasi = rasi;
            this.degrees = degrees;
            this.minutes = minutes;
        }

        /** position within the whole varga chakra in arcminutes - what a comparison uses */
        public int arcminutes() {
            return signIndex(rasi) * 1800 + degrees * 60 + minutes;
        }

        @Override
        public String toString() {
            return String.format("%s %d\u00b0%02d'", rasi, degrees, minutes);
        }
    }

    /**
     * One row of the Bhava Chalit table: where the bhava begins, its madhya - which JHora calls
     * the cusp and which is the <b>middle</b> of the bhava, not its start - where it ends, and
     * the objects that fall in it.
     */
    public static final class Chalit {
        public final double start, madhya, close;
        public final List<String> grahas;

        Chalit(double start, double madhya, double close, List<String> grahas) {
            this.start = start;
            this.madhya = madhya;
            this.close = close;
            this.grahas = grahas;
        }
    }

    private final Map<String, Point> points = new LinkedHashMap<>();
    private final Map<String, Map<String, Cell>> vargas = new LinkedHashMap<>();
    private final Map<String, int[]> bav = new LinkedHashMap<>();
    private final List<Chalit> chalit = new ArrayList<>();
    private final Map<String, String> arudhas = new LinkedHashMap<>();
    private final Map<String, String> avasthas = new LinkedHashMap<>();
    private final List<String> vargaCodes = new ArrayList<>();

    private double ayanamsa;
    private String siderealTime, sunrise, sunset;
    private double tithiRemaining, naksatraRemaining, yogaRemaining, karanaRemaining;
    private String tithiLord, naksatraLord, yogaLord, karanaLord, vaaraLord;

    private JhoraReport() {
    }

    // ------------------------------------------------------------------ parsing

    private static final Pattern POINT = Pattern.compile("^(\\S.*?)\\s{2,}(\\d+)\\s+"
            + "([A-Z][a-z])\\s+(\\d+)'\\s+([\\d.]+)\"\\s+(\\S+)\\s+(\\d)\\s+"
            + "([A-Z][a-z])\\s+([A-Z][a-z])\\s*$");

    private static final Pattern CELL = Pattern.compile("(\\d{1,2})([A-Z][a-z])(\\d{2})");

    /** one "29 Le 55' 17.33"" reading - four capture groups */
    private static final String DMS = "(\\d+)\\s+([A-Z][a-z])\\s+(\\d+)'\\s+([\\d.]+)\"";

    /** the chalit column names the ascendant and the outer planets, which LORDS does not */
    static final Map<String, String> CHALIT_POINTS = new LinkedHashMap<>();

    static {
        CHALIT_POINTS.put("As", "LG");
        CHALIT_POINTS.put("Ur", "SW");
        CHALIT_POINTS.put("Ne", "SM");
        CHALIT_POINTS.put("Pl", "TE");
    }

    public static JhoraReport read(final int year) {
        final String resource = String.format("org/jyotisa/refjhora8/ref%04d.md", year);
        final URL url = JhoraReport.class.getClassLoader().getResource(resource);
        if (null == url) throw new AssertionError("missing JHora reference: " + resource);

        final String text;
        try {
            text = IOUtils.toString(url, UTF_8);
        } catch (IOException unreadable) {
            throw new AssertionError("cannot read " + resource, unreadable);
        }

        final JhoraReport report = new JhoraReport();
        report.parseBasics(section(text, "\u041e\u0441\u043d\u043e\u0432\u043d\u0456 "
                + "\u0434\u0430\u043d\u0456"));                        // Osnovni dani
        report.parsePoints(section(text, "\u0414\u043e\u0432\u0433\u043e\u0442\u0438 "
                + "\u043f\u043b\u0430\u043d\u0435\u0442 \u0456 \u0442\u043e\u0447\u043e\u043a"));
        report.parseVargas(section(text, "\u0414\u043e\u0432\u0433\u043e\u0442\u0438 \u0432 "
                + "\u0443\u0441\u0456\u0445 \u0432\u0430\u0440\u0491\u0430\u0445"));
        report.parseBav(section(text, "\u0410\u0448\u0442\u0430\u043a\u0430\u0432\u0430\u0440"
                + "\u0491\u0430 \u0440\u0430\u0441\u0456-\u043a\u0430\u0440\u0442\u0438:"));
        report.parseChalit(section(text, "\u0411\u0445\u0430\u0432\u0430 "
                + "\u0447\u0430\u043b\u0456\u0442"));                 // Bhava chalit
        report.parseAvasthas(section(text, "\u041e\u0441\u043d\u043e\u0432\u043d\u0456 "
                + "\u0430\u0432\u0430\u0441\u0442\u0445\u0438"));   // Osnovni avasthy
        report.parseArudhas(section(text, "\u0420\u0430\u0441\u0456, "
                + "\u0437\u0430\u0439\u043d\u044f\u0442\u0456 \u0432 "
                + "\u0443\u0441\u0456\u0445 \u0432\u0430\u0440\u0491\u0430\u0445"));
        return report;
    }

    /** the fenced block that follows a heading */
    private static String section(final String text, final String heading) {
        final int at = text.indexOf("## " + heading);
        if (at < 0) throw new AssertionError("no section '" + heading + "'");

        final int open = text.indexOf("```", at);
        final int close = text.indexOf("```", open + 3);
        return text.substring(open + 3, close);
    }

    private void parseBasics(final String block) {
        final Matcher ayan = Pattern.compile("\u0410\u044f\u043d\u0430\u043c\u0448\u0430:"
                + "\\s*(\\d+)-(\\d+)-([\\d.]+)").matcher(block);            // Ayanamsha
        if (!ayan.find()) throw new AssertionError("no ayanamsa in the basics block");

        ayanamsa = Integer.parseInt(ayan.group(1))
                + Integer.parseInt(ayan.group(2)) / 60.
                + Double.parseDouble(ayan.group(3)) / 3600.;

        siderealTime = find(block, "\u0417\u043e\u0440\u044f\u043d\u0438\u0439 "
                + "\u0447\u0430\u0441:\\s*(\\S+)");                          // Zoryanyi chas
        sunrise = find(block, "\u0421\u0445\u0456\u0434 \u0441\u043e\u043d"
                + "\u0446\u044f:\\s*(\\S+)");                                // Skhid sontsya
        sunset = find(block, "\u0417\u0430\u0445\u0456\u0434 \u0441\u043e\u043d"
                + "\u0446\u044f:\\s*(\\S+)");                                // Zakhid sontsya

        final String tithi = "\u0422\u0456\u0442\u0445\u0456";               // Tithi
        final String naksatra = "\u041d\u0430\u043a\u0448\u0430\u0442\u0440\u0430";  // Nakshatra
        final String yoga = "\u0419\u043e\u0433\u0430";                      // Yoga
        final String karana = "\u041a\u0430\u0440\u0430\u043d\u0430";        // Karana

        tithiLord = lord(block, tithi);
        tithiRemaining = percent(block, tithi);
        naksatraLord = lord(block, naksatra);
        naksatraRemaining = percent(block, naksatra);
        yogaLord = lord(block, yoga);
        yogaRemaining = percent(block, yoga);
        karanaLord = lord(block, karana);
        karanaRemaining = percent(block, karana);

        vaaraLord = find(block, "\u0412\u0435\u0434\u0438\u0447\u043d\u0438\u0439 "
                + "\u0434\u0435\u043d\u044c:[^(]*\\(([A-Za-z]{2})\\)");      // Vedychnyi den
    }

    /** the graha in the first parenthesis of a panchanga line: "Shukla (Mo) (3.75% left)" */
    private static String lord(final String block, final String key) {
        return find(block, key + ":[^\\n(]*\\(([A-Za-z]{2})\\)");
    }

    /** the percentage JHora prints, which is what <b>remains</b> of the element */
    private static double percent(final String block, final String key) {
        return Double.parseDouble(find(block, key + ":[^\\n]*\\(([\\d.]+)%"));
    }

    private static String find(final String text, final String regex) {
        final Matcher m = Pattern.compile(regex).matcher(text);
        if (!m.find()) throw new AssertionError("not found: " + regex);
        return m.group(1);
    }

    private void parsePoints(final String block) {
        for (String line : block.split("\\R")) {
            final Matcher m = POINT.matcher(line);
            if (!m.matches()) continue;

            final String raw = m.group(1);
            final String code = OBJECTS.get(strip(raw));
            if (null == code) continue;   // a spuhta or V-point this library does not compute

            final double longitude = signIndex(SIGNS.get(m.group(3))) * 30.
                    + Integer.parseInt(m.group(2))
                    + Integer.parseInt(m.group(4)) / 60.
                    + Double.parseDouble(m.group(5)) / 3600.;

            points.put(code, new Point(code, longitude,
                    SIGNS.get(m.group(8)), SIGNS.get(m.group(9)), m.group(6),
                    Integer.parseInt(m.group(7)), raw.contains("(R)"), karakaOf(raw)));
        }
    }

    /**
     * "Jupiter (R) - Pk" is Jupiter; the retrograde flag and the chara karaka are read separately.
     * <p>
     * The karaka suffix is stripped only when it is a standalone word after " - " - a hyphen
     * inside a name must survive, or "Mrityu-spuhta" would be read as "Mrityu" and overwrite the
     * upagraha of that name. That mistake was made once while this parser was being written.
     */
    private static String strip(final String name) {
        return name.replace(" (R)", "").replaceAll(" - \\S+$", "").trim();
    }

    private static String karakaOf(final String name) {
        final Matcher m = Pattern.compile(" - (\\S+)$").matcher(name);
        return m.find() ? KARAKAS.get(m.group(1)) : null;
    }

    private void parseVargas(final String block) {
        for (String line : block.split("\\R")) {
            if (line.contains("D-1")) {
                final Matcher header = Pattern.compile("D-(\\d+)").matcher(line);
                while (header.find()) vargaCodes.add('D' + header.group(1));
                continue;
            }

            final Matcher first = CELL.matcher(line);
            if (vargaCodes.isEmpty() || !first.find()) continue;

            final String code = OBJECTS.get(strip(line.substring(0, first.start()).trim()));
            if (null == code) continue;

            final List<Cell> cells = new ArrayList<>();
            final Matcher all = CELL.matcher(line);
            while (all.find()) {
                cells.add(new Cell(SIGNS.get(all.group(2)),
                        Integer.parseInt(all.group(1)), Integer.parseInt(all.group(3))));
            }
            if (cells.size() != vargaCodes.size()) continue;

            final Map<String, Cell> perVarga = new LinkedHashMap<>();
            for (int i = 0; i < cells.size(); i++) perVarga.put(vargaCodes.get(i), cells.get(i));
            vargas.put(code, perVarga);
        }
    }

    private void parseBav(final String block) {
        for (String line : block.split("\\R")) {
            final String[] parts = line.trim().split("\\s+");
            if (parts.length < 13) continue;

            final String code = BAV_POINTS.get(parts[0]);
            if (null == code) continue;

            final int[] binduus = new int[12];
            // JHora marks its own strongest cell of a row with a trailing asterisk
            for (int i = 0; i < 12; i++) binduus[i] = Integer.parseInt(parts[i + 1].replace("*", ""));
            bav.put(code, binduus);
        }
    }

    private static final Pattern CHALIT = Pattern.compile(
            "^\\d+\\S*\\s+" + DMS + "\\s+" + DMS + "\\s+" + DMS + "\\s*(.*)$");

    /**
     * "1st  29 Le 55' 17.33"  14 Vi 59' 32.84"  29 Vi 55' 17.33"  As, Ur, Pl"
     * <p>
     * The trailing column is JHora's own two-letter object names, comma separated and often
     * absent - a bhava with nothing in it prints an empty column rather than being omitted.
     */
    private void parseChalit(final String block) {
        for (String line : block.split("\\R")) {
            final Matcher m = CHALIT.matcher(line.trim());
            if (!m.matches()) continue;

            final List<String> grahas = new ArrayList<>();
            for (String name : m.group(13).split(",")) {
                final String code = LORDS.get(name.trim());
                grahas.add(null != code ? code : CHALIT_POINTS.get(name.trim()));
            }
            grahas.removeIf(java.util.Objects::isNull);

            chalit.add(new Chalit(degrees(m, 1), degrees(m, 5), degrees(m, 9), grahas));
        }
    }

    /** four capture groups per DMS: degrees, sign, minutes, seconds */
    private static double degrees(final Matcher m, final int group) {
        return signIndex(SIGNS.get(m.group(group + 1))) * 30.
                + Integer.parseInt(m.group(group))
                + Integer.parseInt(m.group(group + 2)) / 60.
                + Double.parseDouble(m.group(group + 3)) / 3600.;
    }

    private static final Pattern ARUDHA =
            Pattern.compile("^(AL|UL|A\\d{1,2})\\s+([A-Z][a-z])\\s.*$");

    /**
     * The twelve arudha padas, from the D-1 column of "Расі, зайняті в усіх варґах".
     * <p>
     * JHora labels the first and the twelfth by their own names - {@code AL} and {@code UL} -
     * and the other ten by number, which is the same convention {@code EArudhaPada} uses.
     */
    private void parseArudhas(final String block) {
        for (String line : block.split("\\R")) {
            final Matcher m = ARUDHA.matcher(line.trim());
            if (m.matches()) arudhas.put(m.group(1), SIGNS.get(m.group(2)));
        }
    }

    /** the age avastha JHora prints first, in this library's own codes */
    static final Map<String, String> AVASTHAS = new LinkedHashMap<>();

    static {
        AVASTHAS.put("\u0411\u0430\u043b\u0430", "AV1");                       // Bala
        AVASTHAS.put("\u041a\u0443\u043c\u0430\u0440\u0430", "AV2");         // Kumara
        AVASTHAS.put("\u042e\u0432\u0430", "AV3");                              // Yuva
        AVASTHAS.put("\u0412\u0440\u0456\u0434\u0445\u0430", "AV4");         // Vriddha
        AVASTHAS.put("\u041c\u0440\u0456\u0442\u0430", "AV5");                // Mrita
    }

    /**
     * The first column of "Основні авастхи" - the age avastha of each graha.
     * <p>
     * JHora prints it as a Sanskrit name followed by a gloss in brackets, so only the leading word
     * is read. The other two columns of that block - wakefulness and mood - are not parsed:
     * wakefulness needs the dignity and mood is a <i>set</i> per graha, and neither is implemented.
     */
    private void parseAvasthas(final String block) {
        for (String line : block.split("\\R")) {
            final Matcher m = Pattern.compile("^(\\S+)\\s+(\\S+)\\s").matcher(line.trim());
            if (!m.find()) continue;

            final String code = OBJECTS.get(strip(m.group(1)));
            final String avastha = AVASTHAS.get(m.group(2));

            if (null != code && null != avastha) avasthas.put(code, avastha);
        }
    }

    static int signIndex(final String rasi) {
        int index = 0;
        for (String code : SIGNS.values()) {
            if (code.equals(rasi)) return index;
            index++;
        }
        throw new AssertionError("not a sign: " + rasi);
    }

    // ------------------------------------------------------------------ what was read

    public Map<String, Point> points() { return Collections.unmodifiableMap(points); }
    public Map<String, Map<String, Cell>> vargas() { return Collections.unmodifiableMap(vargas); }
    public Map<String, int[]> bav() { return Collections.unmodifiableMap(bav); }
    public List<Chalit> chalit() { return Collections.unmodifiableList(chalit); }
    public Map<String, String> arudhas() { return Collections.unmodifiableMap(arudhas); }
    public Map<String, String> avasthas() { return Collections.unmodifiableMap(avasthas); }
    public List<String> vargaCodes() { return Collections.unmodifiableList(vargaCodes); }

    public double ayanamsa() { return ayanamsa; }
    public String siderealTime() { return siderealTime; }
    public String sunrise() { return sunrise; }
    public String sunset() { return sunset; }

    public double tithiRemaining() { return tithiRemaining; }
    public double naksatraRemaining() { return naksatraRemaining; }
    public double yogaRemaining() { return yogaRemaining; }
    public double karanaRemaining() { return karanaRemaining; }

    public String tithiLord() { return LORDS.get(tithiLord); }
    public String naksatraLord() { return LORDS.get(naksatraLord); }
    public String yogaLord() { return LORDS.get(yogaLord); }
    public String karanaLord() { return LORDS.get(karanaLord); }
    public String vaaraLord() { return LORDS.get(vaaraLord); }
}
