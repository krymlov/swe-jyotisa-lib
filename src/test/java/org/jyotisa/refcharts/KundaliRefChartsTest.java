/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refcharts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.jyotisa.app.Kundali;
import org.jyotisa.rasi.ERasi;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.api.ISweObjects;
import org.swisseph.api.ISweObjectsOptions;
import org.swisseph.app.SweObjects;
import org.swisseph.app.SweObjectsOptions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweAyanamsa.LAHIRI;
import static org.swisseph.app.SweAyanamsa.TRUE_CITRA;
import static org.swisseph.app.SweHouseSystem.PLACIDUS;
import static org.swisseph.app.SweHouseSystem.WHOLE_SIGN;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * {@code org.jyotisa.app.Kundali} rendered through its own {@code toString()} and diffed,
 * column by column, against the Swiss Ephemeris reference program in
 * {@code e:\Github\swisseph}.
 * <p>
 * Input is the Jagannatha Hora reference set from
 * {@code jyotisa-uajhora/etc/v8.0/uk_exe/jhora8uk/test-data/ref<year>.jhd} - one place and
 * time of day, seventeen epochs: <b>4 April, 17:50:40 local, TZ 5:30 East, 81&deg;08'E
 * 16&deg;10'N (Machilipatnam)</b> - read here straight from the {@code .jhd} files rather
 * than retyped, so the inputs cannot drift from the fixture.
 * <p>
 * Two layers are checked. First, everything swetest computes directly (ayanamsa, the ten
 * planets, the mean node, the ascendant) is compared against what the printed horoscope
 * says. Second - and this is the part swetest cannot answer on its own - every derived
 * Jyotisha quantity (rasi, bhava, naksatra pada, tithi, karana, nitya yoga, vaara, Bhrigu
 * Bindu, the five upagrahas) is <b>recomputed independently in this test</b> from swetest's
 * raw longitudes and compared with the library's. A shared formula bug therefore cannot hide
 * behind a shared implementation, because the two sides do not share one.
 * <p>
 * The ephemeris comes from {@code swe-java-lib/ephe} for both sides (see {@link Swetest}) -
 * this project ships only the {@code _18} block, and half these epochs predate it.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class KundaliRefChartsTest {

    /** the printed report rounds to 1/100 of an arc second, i.e. 2.8e-6 degrees */
    static final double DELTA = 1e-5;

    static final double RASI_LEN = 30.;
    static final double NAK_LEN = 360. / 27.;
    static final double PADA_LEN = 360. / 108.;

    /** swetest body name -> the graha code {@code Kundali.toString()} prints */
    static final String[][] BODY_TO_CODE = {
            {"Sun", "SY"}, {"Moon", "CH"}, {"Mercury", "BU"}, {"Venus", "SK"}, {"Mars", "MA"},
            {"Jupiter", "GU"}, {"Saturn", "SA"}, {"Uranus", "SW"}, {"Neptune", "SM"},
            {"Pluto", "TE"}, {"true Node", "RA"}};

    static final String[] RASI_CODES = {"MES", "VRB", "MIT", "KAR", "SIM", "KAN",
            "TUL", "VRC", "DHN", "MAK", "KUM", "MEE"};

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

    // ============================================================ fixture plumbing

    static JhdChart jhd(int year) {
        return JhdChart.read(String.format("org/jyotisa/refcharts/ref%04d.jhd", year));
    }

    /**
     * The primary configuration these tests run under: <b>True Citra ayanamsa with true (not
     * mean) lunar nodes</b>, whole sign houses - the same setup Jagannatha Hora is being
     * compared against. The three deliberate contrast configurations (mean node, Lahiri,
     * Placidus) live in <i>other configurations</i> below, so each of those axes is still
     * covered even though it is no longer the default.
     */
    private KundaliText chart(JhdChart jhd) {
        final ISweObjectsOptions options = new SweObjectsOptions.Builder()
                .ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN).trueNode(true).build();
        final ISweObjects objects = new SweObjects(swissEph(), jhd.julianDate(),
                jhd.geoLocation(), options).completeBuild();
        return KundaliText.parse(new Kundali(KUNDALI_7_KARAKAS, objects).toString());
    }

    /**
     * swetest's own longitudes for the same instant, keyed by body name. The {@code mt} suffix
     * asks swetest for both nodes so {@code "true Node"} is available - {@link #BODY_TO_CODE}
     * maps RA to the true one, matching the {@code trueNode(true)} chart above.
     */
    private Map<String, Double> reference(JhdChart jhd, String... extra) {
        final String[] args = new String[extra.length + 4];
        args[0] = "-p" + Swetest.BODIES + "mt";
        args[1] = "-true";
        args[2] = "-sid" + TRUE_CITRA.fid();
        args[3] = "-fPl";
        System.arraycopy(extra, 0, args, 4, extra.length);
        return Swetest.values(jhd.date(), jhd.utcTime(), args);
    }

    static String rasiCodeOf(double longitude) {
        return RASI_CODES[(int) (fix360(longitude) / RASI_LEN)];
    }

    // ================================================================== ayanamsa

    @ParameterizedTest(name = "{0} ayanamsa")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void ayanamsaMatchesSwetest(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);

        assertEquals("TRUE_CITRA", text.ayanamsaName());
        assertEquals(Swetest.ayanamsa(jhd.date(), jhd.utcTime(), TRUE_CITRA.fid()),
                text.ayanamsa(), DELTA, "ayanamsa in " + year);
    }

    // =================================================================== planets

    @ParameterizedTest(name = "{0} graha longitudes")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void grahaLongitudesMatchSwetest(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);
        final Map<String, Double> ref = reference(jhd);

        for (String[] pair : BODY_TO_CODE) {
            final Double expected = ref.get(pair[0]);
            assertTrue(null != expected, "swetest printed no " + pair[0] + " for " + year);
            assertEquals(expected, text.row(pair[1]).longitude, DELTA,
                    pair[0] + " (" + pair[1] + ") in " + year);
        }
    }

    @ParameterizedTest(name = "{0} Ketu is opposite Rahu")
    @ValueSource(ints = {0, 500, 1000, 1500, 1900, 2000, 2100})
    void ketuIsExactlyOppositeRahu(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(jhd(year));
        assertEquals(fix360(text.row("RA").longitude + 180.), text.row("KE").longitude, DELTA,
                "Ketu must be 180 degrees from Rahu in " + year);
    }

    @ParameterizedTest(name = "{0} lagna")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void lagnaMatchesSwetestAscendant(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);
        final Map<String, Double> ref = reference(jhd,
                Swetest.house(jhd.longitude(), jhd.latitude(), 'W'));

        final Double ascendant = ref.get("Ascendant");
        assertTrue(null != ascendant, "swetest printed no Ascendant for " + year);
        assertEquals(ascendant, text.row("LG").longitude, DELTA, "lagna in " + year);
        // the Janma Lagna row must repeat the very same ascendant
        assertEquals(ascendant, text.row("JL").longitude, DELTA, "janma lagna in " + year);
    }

    // ====================================================== derived: rasi and bhava

    @ParameterizedTest(name = "{0} rasi and bhava")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void rasiAndWholeSignBhavaFollowFromTheLongitudes(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(jhd(year));
        final int lagnaRasi = (int) (fix360(text.row("LG").longitude) / RASI_LEN);

        for (KundaliText.Row row : text.rows().values()) {
            final int rasi = (int) (fix360(row.longitude) / RASI_LEN);
            assertEquals(RASI_CODES[rasi], row.rasi, row.name + " rasi in " + year);

            // whole sign: the bhava is simply the sign distance from the lagna's sign
            final int bhava = (rasi - lagnaRasi + 12) % 12 + 1;
            if (row.bhava.startsWith("B") && !"JL BL HL GL".contains(row.name)) {
                assertEquals("B" + bhava, row.bhava, row.name + " bhava in " + year);
            }
        }
    }

    /**
     * The Bhava column of the four special-lagna rows (Janma, Bhava, Hora, Ghati) must be
     * the sign distance from the ascendant, exactly as the upagraha rows above them are -
     * only Janma Lagna is bhava 1 by definition. Hora Lagna in particular wanders a long way
     * from the ascendant, so a hardcoded "B1" there is visibly wrong.
     */
    @ParameterizedTest(name = "{0} special lagna bhava")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void specialLagnaBhavaIsTheSignDistanceFromTheAscendant(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(jhd(year));
        final int lagnaRasi = (int) (fix360(text.row("LG").longitude) / RASI_LEN);

        for (String lagna : new String[]{"JL", "BL", "HL", "GL"}) {
            final KundaliText.Row row = text.row(lagna);
            final int rasi = (int) (fix360(row.longitude) / RASI_LEN);
            final int bhava = (rasi - lagnaRasi + 12) % 12 + 1;
            assertEquals("B" + bhava, row.bhava, lagna + " bhava in " + year
                    + " (at " + row.longitude + ", ascendant in " + RASI_CODES[lagnaRasi] + ")");
        }
        // Janma Lagna is the ascendant itself, so it is always bhava 1 - a useful control:
        // if the others were right only because they equal the ascendant, this would not hold
        assertEquals("B1", text.row("JL").bhava, "janma lagna is always bhava 1");
    }

    @ParameterizedTest(name = "{0} naksatra pada")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void naksatraPadaFollowsFromTheLongitude(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(jhd(year));

        for (KundaliText.Row row : text.rows().values()) {
            final double lon = fix360(row.longitude);
            final int expectedPada = 1 + ((int) (lon / PADA_LEN)) % 4;
            final int printedPada = row.naksatraPada.charAt(row.naksatraPada.length() - 1) - '0';
            assertEquals(expectedPada, printedPada, row.name + " pada in " + year
                    + " (" + row.naksatraPada + " at " + lon + ")");
        }
    }

    // ============================================================ derived: panchanga

    @ParameterizedTest(name = "{0} panchanga")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void panchangaFollowsFromSuryaAndChandra(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);
        final Map<String, Double> ref = reference(jhd);

        final double surya = ref.get("Sun");
        final double chandra = ref.get("Moon");

        // tithi: 1 + floor((chandra - surya) / 12), 1..30, printed as S1..S15 / K1..K15
        final int tithiIdx = 1 + (int) (fix360(chandra - surya) / 12.);
        final String expectedTithi = tithiIdx <= 15 ? "S" + tithiIdx : "K" + (tithiIdx - 15);
        assertEquals(expectedTithi, text.tithi(), "tithi in " + year);

        // nitya yoga: keyed on the SUM of the two longitudes, 27 divisions
        final int yogaIdx = 1 + (int) (fix360(chandra + surya) / NAK_LEN);
        assertEquals(yogaIdx, nityaYogaIndexOf(text.nityaYoga()),
                "nitya yoga in " + year + " (" + text.nityaYoga() + ")");

        // naksatra of the Moon
        final int nakIdx = 1 + (int) (fix360(chandra) / NAK_LEN);
        assertEquals(nakIdx, naksatraIndexOf(text.naksatra()),
                "naksatra in " + year + " (" + text.naksatra() + ")");
    }

    @ParameterizedTest(name = "{0} vaara")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void vaaraFollowsFromTheJulianDay(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);

        // the julian day comes from swetest, not from the fixture: a bare SweJulianDate
        // leaves julianDay() at 0 until an ISweObjects initialises it
        final double julianDay = Swetest.julianDayUT(jhd.date(), jhd.utcTime());
        // the standard weekday of a julian day, 0 = Sunday (JD 0.0 was a Monday noon)
        final int dayOfWeek = (int) (Math.floor(julianDay + 1.5) % 7);
        final String[] codes = {"SYVR", "CHVR", "MAVR", "BUVR", "GUVR", "SKVR", "SAVR"};
        assertEquals(codes[dayOfWeek], text.vaara(),
                "vaara in " + year + " (JD " + julianDay + ")");
    }

    // ========================================================== derived: bhrigu bindu

    @ParameterizedTest(name = "{0} bhrigu bindu")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void bhriguBinduIsTheMidpointOfChandraAndRahu(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);
        final Map<String, Double> ref = reference(jhd);

        // Bhrigu Bindu is the Chandra/Rahu midpoint taken from the chart's own Rahu, so under the
        // true-node default it is the TRUE node that feeds it - not the mean one
        final double expected = (ref.get("Moon") + ref.get("true Node")) / 2.;
        assertEquals(expected, text.bhriguBindu(), DELTA, "bhrigu bindu in " + year);
        assertEquals(rasiCodeOf(expected), text.bhriguRasi(), "bhrigu bindu rasi in " + year);

        final int lagnaRasi = (int) (fix360(text.row("LG").longitude) / RASI_LEN);
        final int binduRasi = (int) (fix360(expected) / RASI_LEN);
        assertEquals("B" + ((binduRasi - lagnaRasi + 12) % 12 + 1), text.bhriguBhava(),
                "bhrigu bindu bhava in " + year);
    }

    // ============================================================ derived: upagrahas

    @ParameterizedTest(name = "{0} upagrahas")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void upagrahasFollowFromSurya(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);
        final double surya = reference(jhd).get("Sun");

        final double dhuma = fix360(surya + 133. + 20. / 60.);
        final double vyatipaata = fix360(360. - dhuma);
        final double parivesha = fix360(vyatipaata + 180.);
        final double indrachaapa = fix360(360. - parivesha);
        final double upaketu = fix360(indrachaapa + 16. + 40. / 60.);

        assertEquals(dhuma, text.row("DHU").longitude, DELTA, "Dhuma in " + year);
        assertEquals(vyatipaata, text.row("VYA").longitude, DELTA, "Vyatipaata in " + year);
        assertEquals(parivesha, text.row("PAR").longitude, DELTA, "Parivesha in " + year);
        assertEquals(indrachaapa, text.row("CHP").longitude, DELTA, "Indrachaapa in " + year);
        assertEquals(upaketu, text.row("UPK").longitude, DELTA, "Upaketu in " + year);
    }

    // ================================================================ retrograde flags

    @ParameterizedTest(name = "{0} retrograde flags")
    @ValueSource(ints = {0, 500, 1000, 1500, 1900, 2000, 2100})
    void retrogradeFlagsMatchSwetestSpeed(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd);

        // -fPs prints the longitude speed; a negative one is retrograde
        final Map<String, Double> speed = Swetest.values(jhd.date(), jhd.utcTime(),
                "-p" + Swetest.BODIES + "m", "-true", "-sid" + LAHIRI.fid(), "-fPs");

        for (String[] pair : BODY_TO_CODE) {
            final Double s = speed.get(pair[0]);
            if (null == s) continue;
            assertEquals(s < 0., text.row(pair[1]).retrograde,
                    pair[0] + " retrograde flag in " + year + " (speed " + s + ")");
        }
    }

    // ======================================================= other configurations

    private KundaliText chart(JhdChart jhd, ISweObjectsOptions options) {
        final ISweObjects objects = new SweObjects(swissEph(), jhd.julianDate(),
                jhd.geoLocation(), options).completeBuild();
        return KundaliText.parse(new Kundali(KUNDALI_7_KARAKAS, objects).toString());
    }

    /**
     * The mean-node contrast to the true-node default. Keeps the {@code trueNode(false)} path
     * covered now that the primary configuration above uses true nodes, and pins that the two
     * really do resolve to different bodies rather than the flag being ignored.
     */
    @ParameterizedTest(name = "{0} mean node")
    @ValueSource(ints = {0, 500, 1000, 1500, 1900, 2000, 2100})
    void meanNodeChartUsesSwetestMeanNode(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd, new SweObjectsOptions.Builder()
                .ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN).trueNode(false).build());

        final Map<String, Double> ref = Swetest.values(jhd.date(), jhd.utcTime(),
                "-p" + Swetest.BODIES + "mt", "-true", "-sid" + TRUE_CITRA.fid(), "-fPl");

        assertEquals(ref.get("mean Node"), text.row("RA").longitude, DELTA,
                "Rahu must be the MEAN node in " + year);
        assertEquals(fix360(ref.get("mean Node") + 180.), text.row("KE").longitude, DELTA,
                "Ketu opposite the mean node in " + year);

        // the default chart uses the true node; the two must not silently coincide
        assertEquals(ref.get("true Node"), chart(jhd).row("RA").longitude, DELTA,
                "the default chart must still be on the TRUE node in " + year);
    }

    /**
     * The Lahiri contrast to the True Citra default - the other ayanamsa this library is
     * routinely used with, kept covered end to end against swetest.
     */
    @ParameterizedTest(name = "{0} Lahiri ayanamsa")
    @ValueSource(ints = {0, 500, 1000, 1500, 1900, 2000, 2100})
    void lahiriChartMatchesSwetest(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd, new SweObjectsOptions.Builder()
                .ayanamsa(LAHIRI).houseSystem(WHOLE_SIGN).trueNode(true).build());

        assertEquals("LAHIRI", text.ayanamsaName());
        assertEquals(Swetest.ayanamsa(jhd.date(), jhd.utcTime(), LAHIRI.fid()),
                text.ayanamsa(), DELTA, "Lahiri ayanamsa in " + year);

        final Map<String, Double> ref = Swetest.values(jhd.date(), jhd.utcTime(),
                "-p" + Swetest.BODIES + "mt", "-true", "-sid" + LAHIRI.fid(), "-fPl");
        for (String[] pair : BODY_TO_CODE) {
            assertEquals(ref.get(pair[0]), text.row(pair[1]).longitude, DELTA,
                    pair[0] + " under Lahiri in " + year);
        }
    }

    /**
     * With a real (non whole sign) house system the graha rows take their bhava from
     * {@code sweObjects.houses()}, i.e. from {@code swe_house_pos} against the actual cusps,
     * while the upagraha and special lagna rows below them still count whole signs from the
     * ascendant - the only way those points have ever been placed. The two therefore need
     * not agree, and this test pins that they are each internally right rather than pretending
     * one convention governs the whole report: the grahas are checked against swetest's own
     * Placidus cusps, the upagrahas against the sign distance.
     */
    @ParameterizedTest(name = "{0} Placidus bhava")
    @ValueSource(ints = {1900, 2000, 2100})
    void placidusChartTakesGrahaBhavaFromTheRealCusps(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = jhd(year);
        final KundaliText text = chart(jhd, new SweObjectsOptions.Builder()
                .ayanamsa(TRUE_CITRA).houseSystem(PLACIDUS).trueNode(true).build());

        final Map<String, Double> ref = Swetest.values(jhd.date(), jhd.utcTime(),
                "-p" + Swetest.BODIES + "mt", "-true", "-sid" + TRUE_CITRA.fid(), "-fPl",
                Swetest.house(jhd.longitude(), jhd.latitude(), 'P'));

        final double[] cusps = new double[13];
        for (int h = 1; h <= 12; h++) {
            final Double cusp = ref.get("house " + (h < 10 ? " " : "") + h);
            assertTrue(null != cusp, "swetest printed no Placidus cusp " + h + " for " + year);
            cusps[h] = cusp;
        }

        for (String[] pair : BODY_TO_CODE) {
            final KundaliText.Row row = text.row(pair[1]);
            assertEquals("B" + houseOf(row.longitude, cusps), row.bhava,
                    pair[0] + " Placidus bhava in " + year + " (at " + row.longitude + ")");
        }

        // and the upagrahas are still whole sign, counted from the ascendant's sign
        final int lagnaRasi = (int) (fix360(text.row("LG").longitude) / RASI_LEN);
        for (String upagraha : new String[]{"DHU", "VYA", "PAR", "CHP", "UPK"}) {
            final KundaliText.Row row = text.row(upagraha);
            final int rasi = (int) (fix360(row.longitude) / RASI_LEN);
            assertEquals("B" + ((rasi - lagnaRasi + 12) % 12 + 1), row.bhava,
                    upagraha + " stays whole sign in " + year);
        }
    }

    /** which Placidus house a longitude falls in, walking the cusps the way swetest lays them out */
    static int houseOf(double longitude, double[] cusps) {
        final double lon = fix360(longitude);
        for (int h = 1; h <= 12; h++) {
            final double from = cusps[h];
            final double to = cusps[h == 12 ? 1 : h + 1];
            final double span = fix360(to - from);
            if (fix360(lon - from) < span) return h;
        }
        throw new AssertionError("no house contains " + longitude);
    }

    // ============================================================== sanity of the fixture

    @ParameterizedTest(name = "ref{0}.jhd reads back as Machilipatnam")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void everyReferenceFileCarriesTheSamePlaceAndTime(int year) {
        final JhdChart jhd = jhd(year);
        assertEquals(year, jhd.year());
        assertEquals(4, jhd.month());
        assertEquals(4, jhd.day());
        assertEquals(5.5f, jhd.timeZone(), 1e-6, "TZ 5:30 East");
        assertEquals(81 + 8 / 60., jhd.longitude(), 1e-9, "81 deg 08' East");
        assertEquals(16 + 10 / 60., jhd.latitude(), 1e-9, "16 deg 10' North");
        assertEquals(17 + 50 / 60. + 40 / 3600., jhd.localTime(), 1e-9, "17:50:40 local");
        assertEquals("12:20:40", jhd.utcTime());
    }

    // ===================================================================== helpers

    static final String[] NAKSATRA_CODES = {"ASH", "BHA", "KRI", "ROH", "MRG", "ARD", "PUN",
            "PUS", "ASL", "MAG", "PPH", "UPH", "HAS", "CHT", "SWA", "VIS", "ANU", "JYE",
            "MUL", "PSH", "USH", "SHR", "DHA", "SAT", "PBH", "UBH", "REV"};

    static int naksatraIndexOf(String code) {
        for (int i = 0; i < NAKSATRA_CODES.length; i++) {
            if (NAKSATRA_CODES[i].equals(code)) return i + 1;
        }
        throw new AssertionError("unknown naksatra code: " + code);
    }

    static final String[] NITYA_YOGA_CODES = {"VISK", "PREE", "AYUS", "SAUB", "SOBH", "ATIG",
            "SUKA", "DHRI", "SULA", "GAND", "VRID", "DHRU", "VYAG", "HARS", "VAJR", "SDHI",
            "VYAT", "VARI", "PARI", "SIVA", "SDHA", "SADH", "SUBH", "SUKL", "BRAH", "INDR",
            "VAID"};

    static int nityaYogaIndexOf(String code) {
        for (int i = 0; i < NITYA_YOGA_CODES.length; i++) {
            if (NITYA_YOGA_CODES[i].equals(code)) return i + 1;
        }
        throw new AssertionError("unknown nitya yoga code: " + code);
    }
}
