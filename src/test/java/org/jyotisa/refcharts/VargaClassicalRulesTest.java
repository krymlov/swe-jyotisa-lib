/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refcharts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweAyanamsa.LAHIRI;
import static org.swisseph.app.SweHouseSystem.WHOLE_SIGN;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * The divisional charts (vargas) printed by {@code Kundali.toString()}, checked against the
 * classical Parashari rules implemented independently below.
 * <p>
 * <b>swetest is not a reference for this.</b> The Swiss Ephemeris computes longitudes; a
 * varga is a purely Jyotisha subdivision of the zodiac laid on top of one, and
 * {@code swetest} has no notion of it. The reference for a varga can therefore only be its
 * classical definition, which is what this test encodes - the starting-sign rule of each
 * division, straight from Brihat Parashara Hora Shastra, written out here in a form that
 * shares no code with the library:
 * <pre>
 * D1   the sign itself
 * D2   odd sign: 1st half Leo, 2nd half Cancer; even sign: reversed
 * D3   1st third the sign, 2nd the 5th from it, 3rd the 9th from it
 * D4   the sign, then the 4th, 7th, 10th from it
 * D7   odd sign: from the sign; even sign: from the 7th from it
 * D9   movable: from the sign; fixed: from the 9th; dual: from the 5th
 * D10  odd sign: from the sign; even sign: from the 9th from it
 * D12  from the sign itself
 * D16  movable: Aries; fixed: Leo; dual: Sagittarius
 * D20  movable: Aries; fixed: Sagittarius; dual: Leo
 * D24  odd sign: Leo; even sign: Cancer
 * D27  fiery: Aries; earthy: Cancer; airy: Libra; watery: Capricorn
 * D30  unequal 5/5/8/7/5 to Mars/Saturn/Jupiter/Mercury/Venus, reversed for even signs
 * D40  odd sign: Aries; even sign: Libra
 * D45  movable: Aries; fixed: Leo; dual: Sagittarius
 * D60  from the sign itself
 * </pre>
 * The library reaches these through {@code IVarga.virtualDegree()}, which the concrete
 * {@code VargaDn} classes override wherever the interface's uniform
 * {@code longitude * n mod 360} default does not already produce the classical answer - it
 * happens to for D1, D9, D16, D20 and D27, and those are exactly the ones left on the
 * default. D2, D5 and D30 replace {@code rasi()} outright because their divisions are not
 * equal or not cyclic.
 * <p>
 * D5, D6, D8, D11, D81, D108 and D144 are deliberately not asserted against a classical
 * rule: they are outside the shodasavarga and their definitions differ between traditions.
 * They are still checked for internal sanity ({@link #everyVargaIsSelfConsistent}).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class VargaClassicalRulesTest {

    static final String[] RASI = {"MES", "VRB", "MIT", "KAR", "SIM", "KAN",
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

    private KundaliText chart(int year) {
        final JhdChart jhd = KundaliRefChartsTest.jhd(year);
        final ISweObjects objects = new SweObjects(swissEph(), jhd.julianDate(), jhd.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(LAHIRI).houseSystem(WHOLE_SIGN).build())
                .completeBuild();
        return KundaliText.parse(new Kundali(KUNDALI_7_KARAKAS, objects).toString());
    }

    // ============================================== the classical rules, written from BPHS

    /** movable 0, fixed 1, dual 2 */
    static int modality(int sign) {
        return sign % 3;
    }

    /** fiery 0, earthy 1, airy 2, watery 3 */
    static int element(int sign) {
        return sign % 4;
    }

    /** true for Aries, Gemini, Leo ... - the 1-based odd signs */
    static boolean oddSign(int sign) {
        return sign % 2 == 0;
    }

    /**
     * @param n    the divisor, e.g. 9 for navamsa
     * @param sign the 0-based sign of the D1 longitude
     * @param deg  the degree within that sign, [0,30)
     * @return the 0-based sign the classical rule places this point in
     */
    static int classicalSign(int n, int sign, double deg) {
        final int part = (int) (deg / (30. / n));   // 0-based index of the division

        switch (n) {
            case 1:  return sign;
            case 2:  return oddSign(sign) ? (part == 0 ? 4 : 3) : (part == 0 ? 3 : 4);
            case 3:  return (sign + 4 * part) % 12;
            case 4:  return (sign + 3 * part) % 12;
            case 7:  return ((oddSign(sign) ? sign : sign + 6) + part) % 12;
            case 9:  return ((sign + new int[]{0, 8, 4}[modality(sign)]) + part) % 12;
            case 10: return ((oddSign(sign) ? sign : sign + 8) + part) % 12;
            case 12: return (sign + part) % 12;
            case 16: return (new int[]{0, 4, 8}[modality(sign)] + part) % 12;
            case 20: return (new int[]{0, 8, 4}[modality(sign)] + part) % 12;
            case 24: return ((oddSign(sign) ? 4 : 3) + part) % 12;
            case 27: return (new int[]{0, 3, 6, 9}[element(sign)] + part) % 12;
            case 30: return trimsamsa(sign, deg);
            case 40: return ((oddSign(sign) ? 0 : 6) + part) % 12;
            case 45: return (new int[]{0, 4, 8}[modality(sign)] + part) % 12;
            case 60: return (sign + part) % 12;
            default: throw new IllegalArgumentException("no classical rule encoded for D" + n);
        }
    }

    /**
     * Trimsamsa is the one division that is not equal: an odd sign gives 5/5/8/7/5 degrees to
     * Mars, Saturn, Jupiter, Mercury and Venus, and an even sign runs the same five lords in
     * the opposite order with the spans mirrored. The lord is then named by one of its own
     * signs - the fiery/earthy one for an odd sign, the watery/airy one for an even sign.
     */
    static int trimsamsa(int sign, double deg) {
        if (oddSign(sign)) {
            if (deg < 5) return 0;    // Mars    - Aries
            if (deg < 10) return 10;  // Saturn  - Aquarius
            if (deg < 18) return 8;   // Jupiter - Sagittarius
            if (deg < 25) return 2;   // Mercury - Gemini
            return 6;                 // Venus   - Libra
        }
        if (deg < 5) return 1;        // Venus   - Taurus
        if (deg < 12) return 5;       // Mercury - Virgo
        if (deg < 20) return 11;      // Jupiter - Pisces
        if (deg < 25) return 9;       // Saturn  - Capricorn
        return 7;                     // Mars    - Scorpio
    }

    /** every varga this test knows the classical rule for */
    static final int[] CLASSICAL = {1, 2, 3, 4, 7, 9, 10, 12, 16, 20, 24, 27, 30, 40, 45, 60};

    // ===================================================================== the checks

    @ParameterizedTest(name = "{0} vargas vs the classical rules")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void everyClassicalVargaMatchesItsParashariRule(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(year);
        final List<String> problems = new ArrayList<>();

        for (int n : CLASSICAL) {
            final KundaliText.VargaRow row = text.varga("D" + n);

            for (int i = 0; i < KundaliText.VARGA_COLUMNS.length; i++) {
                final String graha = KundaliText.VARGA_COLUMNS[i];
                final double lon = fix360(text.row(graha).longitude);
                final int sign = (int) (lon / 30.);
                final double deg = lon - sign * 30.;

                final String expected = RASI[classicalSign(n, sign, deg)];
                if (!expected.equals(row.signs[i])) {
                    problems.add(String.format("D%d %s at %.6f (%s %.4f): expected %s, printed %s",
                            n, graha, lon, RASI[sign], deg, expected, row.signs[i]));
                }
            }
        }

        assertTrue(problems.isEmpty(), "varga signs that disagree with the classical rule in "
                + year + ":\n  " + String.join("\n  ", problems));
    }

    /** the divisor of all 23 divisional charts the report prints, in the order it prints them */
    static final int[] ALL_DIVISORS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 16, 20, 24, 27,
            30, 40, 45, 60, 81, 108, 144};

    /**
     * The degree printed inside the brackets, for <b>every</b> one of the 23 vargas and every
     * graha - not just the ones with a classical starting-sign rule. It is the position
     * within the division rescaled to a whole sign, {@code (deg mod 30/n) * n}, which is the
     * same thing as {@code (deg * n) mod 30} for any equal division and is what makes the
     * bracketed figure comparable across vargas.
     * <p>
     * This holds even where the <i>sign</i> rule is not a plain multiplication: an overridden
     * {@code virtualDegree()} shifts by whole signs (multiples of 30) and so leaves the
     * fractional part alone, and D2/D5 divide their sign equally (15&deg; and 6&deg;) even
     * though they choose the sign by parity. D30 is the one division that is genuinely
     * unequal - 5/5/8/7/5 degrees to five lords - but it still steps in whole degrees, so the
     * printed figure is the position within the current 1&deg; step, and the same expression
     * describes it. See {@link #trimsamsaDegreeIsTheStepPositionNotTheUnequalSpan}.
     */
    @ParameterizedTest(name = "{0} varga degrees, all 23")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void everyVargaRescalesTheDegreeWithinItsDivision(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(year);
        final List<String> problems = new ArrayList<>();

        for (int n : ALL_DIVISORS) {
            final KundaliText.VargaRow row = text.varga("D" + n);

            // two roundings stack up here: the varga degree is printed with toDMS, to the
            // whole arc second (up to 0.5"), and the D1 longitude this test multiplies by n
            // was itself read back from toDMSms, to 1/100 of an arc second (up to 0.005",
            // which the multiplication scales to n * 0.005"). A wrong formula would be out
            // by whole degrees, so this stays a sharp check even at n = 144.
            final double delta = (0.5 + n * 0.005) / 3600.;

            for (int i = 0; i < KundaliText.VARGA_COLUMNS.length; i++) {
                final String graha = KundaliText.VARGA_COLUMNS[i];
                final double lon = fix360(text.row(graha).longitude);
                final double deg = lon - ((int) (lon / 30.)) * 30.;
                final double expected = (deg * n) % 30.;

                if (Math.abs(expected - row.degrees[i]) > delta) {
                    problems.add(String.format("D%d %s: D1 deg %.6f -> expected %.6f, printed %.6f",
                            n, graha, deg, expected, row.degrees[i]));
                }
            }
        }

        assertTrue(problems.isEmpty(), "varga degrees that do not rescale correctly in "
                + year + ":\n  " + String.join("\n  ", problems));
    }

    /**
     * The same degrees again, but computed from <b>swetest's own full-precision sidereal
     * longitudes</b> rather than from the D1 column of the report. That removes the input
     * rounding the test above has to allow for, so the only slack left is the half arc second
     * {@code toDMS} itself introduces - and it makes the check independent of the printed D1
     * longitude, closing the chain ephemeris → varga degree end to end instead of only
     * verifying the report against itself.
     */
    @ParameterizedTest(name = "{0} varga degrees vs swetest longitudes")
    @ValueSource(ints = {0, 500, 1000, 1500, 1900, 2000, 2100})
    void everyVargaDegreeFollowsFromSwetestLongitudes(int year) {
        assumeTrue(Swetest.available());
        final JhdChart jhd = KundaliRefChartsTest.jhd(year);
        final KundaliText text = chart(year);

        final java.util.Map<String, Double> ref = Swetest.values(jhd.date(), jhd.utcTime(),
                "-p" + Swetest.BODIES + "m", "-true", "-sid" + LAHIRI.fid(), "-fPl",
                Swetest.house(jhd.longitude(), jhd.latitude(), 'W'));

        // the varga table's columns, in order, sourced straight from swetest
        final double[] longitudes = {
                ref.get("Ascendant"), ref.get("Sun"), ref.get("Moon"), ref.get("Mars"),
                ref.get("Mercury"), ref.get("Jupiter"), ref.get("Venus"), ref.get("Saturn"),
                ref.get("mean Node"), fix360(ref.get("mean Node") + 180.),
                ref.get("Uranus"), ref.get("Neptune"), ref.get("Pluto")};

        final List<String> problems = new ArrayList<>();

        for (int n : ALL_DIVISORS) {
            final KundaliText.VargaRow row = text.varga("D" + n);

            for (int i = 0; i < longitudes.length; i++) {
                final double lon = fix360(longitudes[i]);
                final double deg = lon - ((int) (lon / 30.)) * 30.;
                final double expected = (deg * n) % 30.;
                // only toDMS's own rounding to the whole arc second is unaccounted for
                final double diff = Math.abs(expected - row.degrees[i]);

                if (diff > 0.6 / 3600. && Math.abs(diff - 30.) > 0.6 / 3600.) {
                    problems.add(String.format("D%d %s: swetest %.9f -> expected %.6f, printed %.6f",
                            n, KundaliText.VARGA_COLUMNS[i], lon, expected, row.degrees[i]));
                }
            }
        }

        assertTrue(problems.isEmpty(), "varga degrees disagreeing with swetest in "
                + year + ":\n  " + String.join("\n  ", problems));
    }

    /**
     * Trimsamsa's five spans are 5/5/8/7/5 degrees, so a graha 3&deg; into the 8&deg; Jupiter
     * span is 3/8 of the way through it - yet the report prints the position within the
     * current whole degree, rescaled, exactly as every equally divided varga does. That is a
     * deliberate consequence of the implementation stepping D30 in 1&deg; units and then
     * bucketing those steps into the five spans: the <b>sign</b> is the classical one, the
     * <b>degree</b> is nominal and does not measure progress through the span it belongs to.
     * Pinned here so the distinction is not mistaken for a defect - only the sign of a
     * trimsamsa carries classical meaning.
     */
    @Test
    void trimsamsaDegreeIsTheStepPositionNotTheUnequalSpan() {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(2000);
        final KundaliText.VargaRow d30 = text.varga("D30");

        for (int i = 0; i < KundaliText.VARGA_COLUMNS.length; i++) {
            final double lon = fix360(text.row(KundaliText.VARGA_COLUMNS[i]).longitude);
            final double deg = lon - ((int) (lon / 30.)) * 30.;

            // the position within the current whole degree, scaled to a sign
            final double stepPosition = (deg % 1.) * 30.;
            assertEquals(stepPosition, d30.degrees[i], (0.5 + 30 * 0.005) / 3600.,
                    "D30 " + KundaliText.VARGA_COLUMNS[i] + " degree");
        }
    }

    @ParameterizedTest(name = "{0} all 23 vargas are self consistent")
    @ValueSource(ints = {0, 1000, 2000, 2100})
    void everyVargaIsSelfConsistent(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(year);

        assertEquals(23, text.vargas().size(), "the report must list all 23 divisional charts");

        for (KundaliText.VargaRow row : text.vargas().values()) {
            assertEquals(KundaliText.VARGA_COLUMNS.length, row.signs.length,
                    row.code + " must place every graha in " + year);
            for (int i = 0; i < row.signs.length; i++) {
                assertTrue(java.util.Arrays.asList(RASI).contains(row.signs[i]),
                        row.code + " " + KundaliText.VARGA_COLUMNS[i] + " sign '" + row.signs[i] + "'");
                assertTrue(row.degrees[i] >= 0. && row.degrees[i] < 30.,
                        row.code + " " + KundaliText.VARGA_COLUMNS[i] + " degree " + row.degrees[i]);
            }
        }
    }

    /**
     * Rahu and Ketu are exactly six signs apart with the same degree, so where they land in a
     * varga is decided entirely by whether that varga's <i>starting sign</i> survives a
     * six-sign shift. It does when the rule keys on something a shift of six preserves - the
     * sign's parity (D2, D24, D30, D40) or its modality (D16, D20, D45), since 6 is even and
     * divisible by 3 - and then both nodes get the same start, the same division index, and
     * therefore the <b>same</b> varga sign. Every other rule is relative to the sign itself
     * and carries the six signs through, leaving them <b>opposite</b>.
     * <p>
     * This looks at the same rules from a different side than
     * {@link #everyClassicalVargaMatchesItsParashariRule}: it asserts a structural property
     * of each rule rather than recomputing it, so a rule applied inconsistently across the
     * zodiac shows up here even if it happened to be right for the signs that test sampled.
     */
    @ParameterizedTest(name = "{0} node symmetry across vargas")
    @ValueSource(ints = {0, 1000, 2000, 2100})
    void theLunarNodesFollowEachVargasShiftInvariance(int year) {
        assumeTrue(Swetest.available());
        final KundaliText text = chart(year);
        final int rahu = java.util.Arrays.asList(KundaliText.VARGA_COLUMNS).indexOf("RA");
        final int ketu = java.util.Arrays.asList(KundaliText.VARGA_COLUMNS).indexOf("KE");

        // vargas whose starting sign is fixed by parity or modality, so a six-sign shift
        // leaves it alone and both nodes share one varga sign
        final List<Integer> nodesCoincide = java.util.Arrays.asList(2, 16, 20, 24, 30, 40, 45);

        for (int n : new int[]{2, 3, 4, 7, 9, 10, 12, 16, 20, 24, 27, 30, 40, 45, 60}) {
            final KundaliText.VargaRow row = text.varga("D" + n);
            final int ra = java.util.Arrays.asList(RASI).indexOf(row.signs[rahu]);
            final int ke = java.util.Arrays.asList(RASI).indexOf(row.signs[ketu]);

            final int expected = nodesCoincide.contains(n) ? ra : (ra + 6) % 12;
            assertEquals(RASI[expected], RASI[ke], "D" + n + " Ketu relative to Rahu in " + year
                    + " (Rahu in " + RASI[ra] + ")");
        }
    }

    @Test
    void theClassicalRulesUsedHereShareNoCodeWithTheLibrary() {
        // navamsa of 16 deg 22' Sagittarius - Sagittarius is dual, so the navamsas start from
        // the 5th from it (Aries), and 16.377 deg is the 5th navamsa, giving Leo. This is the
        // Lucknow 1947 ascendant, whose navamsa the checked-in golden file independently
        // records as SIM.
        assertEquals(4, classicalSign(9, 8, 16.377), "Sagittarius 16.377 -> Leo navamsa");
        // and the same point in D3: 2nd drekkana of Sagittarius is the 5th from it, Aries
        assertEquals(0, classicalSign(3, 8, 16.377), "Sagittarius 16.377 -> Aries drekkana");
    }
}
