/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refcharts;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parses the horoscope text {@code org.jyotisa.app.Kundali.toString()} renders, so the
 * printed report itself - not just the objects behind it - can be diffed against
 * {@code swetest}. Every graha/upagraha/lagna row looks like
 * <pre>
 * SY   = 118°38'07.16" -&gt; Rasi= KAR (95.45%) | 28°38'07.16" -&gt; Naksatra= ASL4|BU (89.76%)
 *        -&gt; Navamsa= MEE|GU -&gt; Bhava= B8  -&gt; Dignity= MIR -&gt;  AK -&gt;
 * </pre>
 * split on {@code ->} rather than matched with one long regex, because the columns are
 * width-padded and several of them are optional (upagraha and lagna rows stop at Bhava).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public final class KundaliText {

    /** one parsed graha / upagraha / lagna row */
    public static final class Row {
        /** the graha code with the retrograde parentheses stripped, e.g. {@code RA} not {@code (RA)} */
        public final String name;
        /** {@code Kundali.toString()} wraps a retrograde graha's code in parentheses */
        public final boolean retrograde;
        public final double longitude;
        public final String rasi;
        public final double rasiProgress;
        public final double degreeInRasi;
        public final String naksatraPada;
        public final String naksatraLord;
        public final double naksatraProgress;
        public final String navamsa;
        public final String navamsaLord;
        public final String bhava;
        public final String dignity;
        public final String karaka;

        Row(String name, boolean retrograde, double longitude, String rasi, double rasiProgress,
            double degreeInRasi, String naksatraPada, String naksatraLord, double naksatraProgress,
            String navamsa, String navamsaLord, String bhava, String dignity, String karaka) {
            this.name = name;
            this.retrograde = retrograde;
            this.longitude = longitude;
            this.rasi = rasi;
            this.rasiProgress = rasiProgress;
            this.degreeInRasi = degreeInRasi;
            this.naksatraPada = naksatraPada;
            this.naksatraLord = naksatraLord;
            this.naksatraProgress = naksatraProgress;
            this.navamsa = navamsa;
            this.navamsaLord = navamsaLord;
            this.bhava = bhava;
            this.dignity = dignity;
            this.karaka = karaka;
        }

        @Override
        public String toString() {
            return name + "=" + longitude + " " + rasi + " " + naksatraPada + " " + bhava;
        }
    }

    /** one {@code Dn} line of the varga table: the sign and degree of each graha, in EGraha order */
    public static final class VargaRow {
        public final String code;
        public final String[] signs;
        public final double[] degrees;

        VargaRow(String code, String[] signs, double[] degrees) {
            this.code = code;
            this.signs = signs;
            this.degrees = degrees;
        }
    }

    /** the grahas the varga table lists, in the order {@code Kundali.toString()} prints them */
    public static final String[] VARGA_COLUMNS = {"LG", "SY", "CH", "MA", "BU", "GU", "SK",
            "SA", "RA", "KE", "SW", "SM", "TE"};

    private static final java.util.regex.Pattern VARGA_CELL =
            java.util.regex.Pattern.compile("([A-Z]{3})\\[([^\\]]+)\\]");

    private final Map<String, VargaRow> vargas = new LinkedHashMap<>();
    private final Map<String, Row> rows = new LinkedHashMap<>();
    private String ayanamsaName;
    private double ayanamsa;
    private String houseSystem, naksatra, tithi, vaara, nityaYoga, karana;
    private double bhriguBindu;
    private String bhriguBhava, bhriguRasi;

    private KundaliText() {
    }

    public static KundaliText parse(String text) {
        final KundaliText out = new KundaliText();
        for (String line : text.split("\\R")) {
            final String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            if (trimmed.startsWith("Ayanamsa:")) out.parseAyanamsaLine(trimmed);
            else if (trimmed.startsWith("Housesys:")) out.parsePanchangaLine(trimmed);
            else if (trimmed.startsWith("Bhrigu Bindu:")) out.parseBhriguLine(trimmed);
            else if (trimmed.contains("-> Rasi=")) {
                final Row row = parseRow(trimmed);
                out.rows.put(row.name, row);
            } else if (trimmed.matches("^D\\d+\\s*=.*")) {
                final VargaRow varga = parseVargaRow(trimmed);
                out.vargas.put(varga.code, varga);
            }
        }
        return out;
    }

    private void parseAyanamsaLine(String line) {
        // Ayanamsa: LAHIRI|23°07'18.71", Event Date: ..., Location: ...
        final String head = line.substring(line.indexOf(':') + 1, line.indexOf(", Event Date:")).trim();
        final int bar = head.indexOf('|');
        this.ayanamsaName = head.substring(0, bar).trim();
        this.ayanamsa = Swetest.parseDms(head.substring(bar + 1).trim());
    }

    private void parsePanchangaLine(String line) {
        for (String part : line.split(",")) {
            final int colon = part.indexOf(':');
            if (colon < 0) continue;
            final String key = part.substring(0, colon).trim();
            String value = part.substring(colon + 1).trim();
            final int paren = value.indexOf('(');
            if (paren > 0) value = value.substring(0, paren).trim();

            switch (key) {
                case "Housesys": houseSystem = value; break;
                case "Naksatra": naksatra = value; break;
                case "Tithi": tithi = value; break;
                case "Vaara": vaara = value; break;
                case "Nitya Yoga": nityaYoga = value; break;
                case "Karana": karana = value; break;
                default: break;
            }
        }
    }

    private void parseBhriguLine(String line) {
        // Bhrigu Bindu: 69°32'53.15", Bhava: B7, Rasi: MIT, Naksatra: ARD
        for (String part : line.split(",")) {
            final int colon = part.indexOf(':');
            if (colon < 0) continue;
            final String key = part.substring(0, colon).trim();
            final String value = part.substring(colon + 1).trim();
            switch (key) {
                case "Bhrigu Bindu": bhriguBindu = Swetest.parseDms(value); break;
                case "Bhava": bhriguBhava = value; break;
                case "Rasi": bhriguRasi = value; break;
                default: break;
            }
        }
    }

    static Row parseRow(String line) {
        final String[] cols = line.split("->");
        final String[] head = cols[0].split("=", 2);
        final String rawName = head[0].trim();
        final boolean retrograde = rawName.startsWith("(") && rawName.endsWith(")");
        final String name = retrograde ? rawName.substring(1, rawName.length() - 1).trim() : rawName;
        final double longitude = Swetest.parseDms(head[1].trim());

        // " Rasi= KAR (95.45%) | 28°38'07.16" "
        final String rasiCol = cols[1];
        final String rasi = between(rasiCol, "Rasi=", "(").trim();
        final double rasiProgress = Double.parseDouble(between(rasiCol, "(", "%").trim());
        final double degreeInRasi = Swetest.parseDms(rasiCol.substring(rasiCol.indexOf('|') + 1).trim());

        // " Naksatra= ASL4|BU (89.76%) "
        final String nakCol = cols[2];
        final String nakBody = between(nakCol, "Naksatra=", "(").trim();
        final String naksatraPada = nakBody.substring(0, nakBody.indexOf('|')).trim();
        final String naksatraLord = nakBody.substring(nakBody.indexOf('|') + 1).trim();
        final double naksatraProgress = Double.parseDouble(between(nakCol, "(", "%").trim());

        // " Navamsa= MEE|GU "
        final String navBody = cols[3].substring(cols[3].indexOf('=') + 1).trim();
        final String navamsa = navBody.substring(0, navBody.indexOf('|')).trim();
        final String navamsaLord = navBody.substring(navBody.indexOf('|') + 1).trim();

        final String bhava = cols[4].substring(cols[4].indexOf('=') + 1).trim();

        // upagraha and lagna rows stop at Bhava; graha rows carry Dignity, Karaka, MrityuBhaga
        final String dignity = cols.length > 5 ? cols[5].substring(cols[5].indexOf('=') + 1).trim() : null;
        final String karaka = cols.length > 6 ? cols[6].trim() : null;

        return new Row(name, retrograde, longitude, rasi, rasiProgress, degreeInRasi, naksatraPada,
                naksatraLord, naksatraProgress, navamsa, navamsaLord, bhava, dignity, karaka);
    }

    /** "D9\t=  VRB[17°19'04"] MAK[10°38'39"] ..." */
    static VargaRow parseVargaRow(String line) {
        final int eq = line.indexOf('=');
        final String code = line.substring(0, eq).trim();

        final java.util.List<String> signs = new java.util.ArrayList<>();
        final java.util.List<Double> degrees = new java.util.ArrayList<>();
        final java.util.regex.Matcher m = VARGA_CELL.matcher(line.substring(eq + 1));
        while (m.find()) {
            signs.add(m.group(1));
            degrees.add(Swetest.parseDms(m.group(2)));
        }

        final double[] deg = new double[degrees.size()];
        for (int i = 0; i < deg.length; i++) deg[i] = degrees.get(i);
        return new VargaRow(code, signs.toArray(new String[0]), deg);
    }

    public VargaRow varga(String code) {
        final VargaRow row = vargas.get(code);
        if (null == row) throw new AssertionError("no varga '" + code + "' in " + vargas.keySet());
        return row;
    }

    public Map<String, VargaRow> vargas() { return vargas; }

    private static String between(String s, String from, String to) {
        final int a = s.indexOf(from) + from.length();
        final int b = s.indexOf(to, a);
        return b < 0 ? s.substring(a) : s.substring(a, b);
    }

    public Row row(String name) {
        final Row row = rows.get(name);
        if (null == row) throw new AssertionError("no row '" + name + "' in " + rows.keySet());
        return row;
    }

    public boolean has(String name) { return rows.containsKey(name); }
    public Map<String, Row> rows() { return rows; }
    public String ayanamsaName() { return ayanamsaName; }
    public double ayanamsa() { return ayanamsa; }
    public String houseSystem() { return houseSystem; }
    public String naksatra() { return naksatra; }
    public String tithi() { return tithi; }
    public String vaara() { return vaara; }
    public String nityaYoga() { return nityaYoga; }
    public String karana() { return karana; }
    public double bhriguBindu() { return bhriguBindu; }
    public String bhriguBhava() { return bhriguBhava; }
    public String bhriguRasi() { return bhriguRasi; }
}
