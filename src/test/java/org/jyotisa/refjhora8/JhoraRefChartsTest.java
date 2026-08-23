/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refjhora8;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.bhava.IBhavaChalita;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.api.arudha.IArudhaPada;
import org.jyotisa.api.arudha.IArudhaPadas;
import org.jyotisa.arudha.EArudhaPada;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.avastha.EAvastha;
import org.jyotisa.app.Kundali;
import org.jyotisa.refcharts.JhdChart;
import org.jyotisa.refcharts.KundaliText;
import org.jyotisa.refcharts.Swetest;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;
import org.swisseph.app.SweObjectsOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweJulianDate.SE_GREG_CAL;
import static org.swisseph.app.SweAyanamsa.TRUE_CITRA;
import static org.swisseph.app.SweHouseSystem.WHOLE_SIGN;

/**
 * {@code Kundali.toString()} against <b>Jagannatha Hora</b>, at the seventeen reference epochs.
 *
 * <h2>Two things had to be right before any of this could be compared</h2>
 * <ol>
 * <li><b>True Chitrapaksha ayanamsa</b> - the configuration these dumps were taken under. With
 *     any other, every sidereal figure in the file shifts.</li>
 * <li><b>The proleptic Gregorian calendar.</b> JHora reads "4 April 1000" as a Gregorian date,
 *     while this library deduces the calendar and reads it as Julian - six days apart, a
 *     different chart entirely. It is invisible at the twelve modern epochs and total at the five
 *     before 1582: without {@link org.swisseph.api.ISweJulianDate#SE_GREG_CAL} the year 1000
 *     ascendant comes out in a different <i>sign</i>. That is why these tests build their own
 *     chart rather than reusing the golden reports of {@code org.jyotisa.refcharts}, which
 *     deliberately keep the deduced calendar.</li>
 * </ol>
 *
 * <h2>What agrees</h2>
 * At the modern epochs everything does, to the last figure either program prints: the D-1
 * longitudes of all 32 common objects to well under an arcsecond, all fifteen comparable vargas
 * of all thirteen grahas at JHora's own one-arcminute rendering, rasi, navamsa, pada, the
 * panchanga, sidereal time, sunrise and sunset.
 *
 * <h2>What does not, and why each is expected</h2>
 * <ul>
 * <li><b>D-2</b> - a genuine difference of rule, not of arithmetic. See
 *     {@link #d2IsTheOneVargaWhoseRuleDiffers()}.</li>
 * <li><b>The ancient epochs</b> - delta t. Two independent bodies imply the same time offset and
 *     the divergence decays to nothing by 1900. See
 *     {@link #theAncientEpochsDivergeThroughDeltaTAndNothingElse()}.</li>
 * <li><b>The ayanamsa figure</b> - a display convention: JHora prints it without nutation.</li>
 * <li><b>Two cells of the Moon's Bhinnashtakavarga</b> - see
 *     {@link #ashtakavargaAgreesExceptTwoCellsOfTheMoonsOwnRow()}.</li>
 * </ul>
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see JhoraReport
 */
class JhoraRefChartsTest {

    /**
     * The epochs at which the two delta t models have converged, so a disagreement here is a
     * disagreement about a rule. Below 1900 the models differ by tens of seconds at year 0.
     */
    private static final int[] MODERN = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100};

    /** every epoch, modern and ancient */
    private static final int[] ALL = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100};

    /**
     * JHora renders a varga cell to the arcminute and rounds it, so two values a whisker apart
     * can land on either side. One arcminute is the finest a comparison against this file can be.
     */
    private static final int ONE_ARCMINUTE = 1;

    /** what the two programs agree to on a D-1 longitude at the modern epochs */
    private static final double ARCSECOND = 1. / 3600.;

    /**
     * The same fixture {@code org.jyotisa.refcharts} drives its own suite from - one place and
     * one time of day at seventeen epochs, authored in Jagannatha Hora's own {@code .jhd} format.
     */
    private static JhdChart jhd(final int year) {
        return JhdChart.read(String.format("org/jyotisa/refcharts/ref%04d.jhd", year));
    }

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

    private static void requireEphemeris() {
        assumeTrue(Swetest.EPHE.isDirectory() && new File(Swetest.EPHE, "sepl_18.se1").isFile(),
                "ephemeris not available at " + Swetest.EPHE);
    }

    /**
     * The chart JHora was given: True Citra, true nodes, whole sign - and the date read the way
     * JHora reads it, as proleptic Gregorian.
     */
    private KundaliText ours(final int year) {
        final JhdChart jhd = jhd(year);

        final ISweObjects objects = new SweObjects(swissEph(),
                ((SweJulianDate) jhd.julianDate()).calendar(SE_GREG_CAL), jhd.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build()).completeBuild();

        return KundaliText.parse(new Kundali(KUNDALI_7_KARAKAS, objects).toString());
    }

    /** the shortest arc between two longitudes, in degrees */
    private static double apart(final double a, final double b) {
        final double d = Math.abs(a - b);
        return Math.min(d, 360. - d);
    }

    /** the shortest distance between two positions in a varga chakra, in arcminutes */
    private static int apart(final int a, final int b) {
        final int d = Math.abs(a - b);
        return Math.min(d, 21600 - d);
    }

    /** JHora's rendering of one of our cells: whole degrees, minutes rounded and never carried */
    private static int asJhoraRenders(final String rasi, final double degreeInRasi) {
        final int degrees = (int) degreeInRasi;
        final double minutes = (degreeInRasi - degrees) * 60.;

        return JhoraReport.signIndex(rasi) * 1800 + degrees * 60 + (int) Math.round(minutes);
    }

    // ============================================================ the headline: the vargas

    /**
     * <b>The longitudes in every varga</b>, which is what these files are here for: all sixteen
     * divisional charts times thirteen grahas at each modern epoch - 2080 cells - compared at
     * JHora's own rendering precision.
     * <p>
     * D-2 is included since 2026-08-22, when this library adopted JHora's hora scheme in place of
     * the classical one. Before that it was the single varga that could not be compared.
     */
    @ParameterizedTest(name = "ref{0} vargas")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void vargaLongitudesAgreeWithJagannathaHora(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        final List<String> problems = new ArrayList<>();
        int compared = 0;

        for (Map.Entry<String, Map<String, JhoraReport.Cell>> perObject : jhora.vargas().entrySet()) {
            final int column = columnOf(perObject.getKey());
            if (column < 0) continue;    // an object the varga table of this library does not list

            for (Map.Entry<String, JhoraReport.Cell> perVarga : perObject.getValue().entrySet()) {
                final String code = perVarga.getKey();
                if (!ours.vargas().containsKey(code)) continue;

                final KundaliText.VargaRow row = ours.varga(code);
                final int mine = asJhoraRenders(row.signs[column], row.degrees[column]);
                final int theirs = perVarga.getValue().arcminutes();
                compared++;

                if (apart(mine, theirs) > ONE_ARCMINUTE) {
                    problems.add(year + " " + perObject.getKey() + " " + code
                            + ": jhora " + perVarga.getValue()
                            + " vs ours " + row.signs[column]
                            + String.format(" %.4f", row.degrees[column]));
                }
            }
        }

        assertTrue(compared > 200, "expected the whole varga table, compared " + compared);
        assertTrue(problems.isEmpty(), problems.size() + " of " + compared
                + " varga cells differ:\n  " + String.join("\n  ", problems));
    }

    private static int columnOf(final String code) {
        for (int i = 0; i < KundaliText.VARGA_COLUMNS.length; i++) {
            if (KundaliText.VARGA_COLUMNS[i].equals(code)) return i;
        }
        return -1;
    }

    /**
     * <b>D-2 agrees, and this pins the rule it now follows.</b>
     *
     * <h2>What changed</h2>
     * Until 2026-08-22 this library computed the classical Parashari hora, under which the two
     * halves of a sign belong to the Moon and the Sun, so every object landed in Cancer or Leo and
     * <b>nowhere else</b>. Jagannatha Hora's "D-2 (US)" spreads them across all twelve signs, and
     * on the author's decision the library now follows JHora.
     * <p>
     * The rule was recovered from JHora's own output rather than from a text - fitted against
     * these seventeen reports and verified on every object placement in them. This test keeps
     * that evidence standing: it asserts both that the two now agree cell for cell, and that the
     * result really does use the whole zodiac rather than the two signs the old rule allowed.
     */
    @Test
    void d2NowFollowsJagannathaHorasHoraScheme() {
        requireEphemeris();

        final Set<String> signsUsed = new LinkedHashSet<>();
        int cells = 0;

        for (int year : ALL) {
            final JhoraReport jhora = JhoraReport.read(year);
            final KundaliText.VargaRow row = ours(year).varga("D2");

            for (Map.Entry<String, Map<String, JhoraReport.Cell>> perObject
                    : jhora.vargas().entrySet()) {

                final int column = columnOf(perObject.getKey());
                final JhoraReport.Cell cell = perObject.getValue().get("D2");
                if (column < 0 || null == cell) continue;

                signsUsed.add(row.signs[column]);
                cells++;

                // the ancient epochs drift through delta t like every other varga, so the
                // agreement is asserted only where the models have converged
                if (year < 1900) continue;

                assertEquals(0, apart(asJhoraRenders(row.signs[column], row.degrees[column]),
                                cell.arcminutes()), ONE_ARCMINUTE,
                        year + " " + perObject.getKey() + " D2: jhora " + cell
                                + " vs ours " + row.signs[column]
                                + String.format(" %.4f", row.degrees[column]));
            }
        }

        assertTrue(cells > 200, "expected D-2 at every epoch, compared " + cells);
        assertTrue(signsUsed.size() > 8, "the JHora hora uses the whole zodiac, not just the"
                + " Moon's and the Sun's signs as the classical one does - saw " + signsUsed);
    }

    // ============================================================ the D-1 longitudes

    /**
     * The strongest check in this file, and the one the varga table rests on: the full-precision
     * D-1 longitude of every object both programs compute - grahas, upagrahas, the eight special
     * lagnas and Bhrigu Bindu - agreeing to under an arcsecond.
     */
    @ParameterizedTest(name = "ref{0} longitudes")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void d1LongitudesAgreeToTheArcsecond(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        int compared = 0;
        for (JhoraReport.Point point : jhora.points().values()) {
            if (!ours.has(point.code)) continue;

            final KundaliText.Row row = ours.row(point.code);
            assertEquals(0., apart(point.longitude, row.longitude), ARCSECOND,
                    year + " " + point.code + ": jhora " + point.longitude
                            + " vs ours " + row.longitude);
            compared++;
        }

        assertTrue(compared >= 30, "expected every common object, compared " + compared);
    }

    @ParameterizedTest(name = "ref{0} rasi, navamsa and pada")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void rasiNavamsaAndPadaAgree(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        for (JhoraReport.Point point : jhora.points().values()) {
            if (!ours.has(point.code)) continue;
            final KundaliText.Row row = ours.row(point.code);

            assertEquals(point.rasi, row.rasi, year + " " + point.code + " rasi");

            // Varnada Lagna is placed in the divisional charts by a rule of its own - see
            // varnadaLagnaIsDividedByADifferentRule()
            if (!"VL".equals(point.code)) {
                assertEquals(point.navamsa, row.navamsa, year + " " + point.code + " navamsa");
            }

            // this library prints the naksatra and its quarter as one token, "HAS2"
            final char quarter = row.naksatraPada.charAt(row.naksatraPada.length() - 1);
            assertEquals(point.pada, quarter - '0', year + " " + point.code + " pada");
        }
    }

    /**
     * The naksatra itself, without needing a dictionary of JHora's Ukrainian abbreviations: each
     * label must correspond to one and only one naksatra of this library, across all seventeen
     * charts. A boundary that disagreed would show up as a label naming two.
     */
    @Test
    void everyNaksatraLabelNamesExactlyOneOfOurs() {
        requireEphemeris();

        final Map<String, Set<String>> byLabel = new TreeMap<>();

        for (int year : MODERN) {
            final JhoraReport jhora = JhoraReport.read(year);
            final KundaliText ours = ours(year);

            for (JhoraReport.Point point : jhora.points().values()) {
                if (!ours.has(point.code)) continue;

                final String pada = ours.row(point.code).naksatraPada;
                byLabel.computeIfAbsent(point.naksatra, k -> new LinkedHashSet<>())
                        .add(pada.substring(0, pada.length() - 1));
            }
        }

        final List<String> ambiguous = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : byLabel.entrySet()) {
            if (entry.getValue().size() > 1) ambiguous.add(entry.getKey() + " -> " + entry.getValue());
        }

        assertTrue(byLabel.size() > 15, "expected many naksatras across ten charts, saw " + byLabel.size());
        assertTrue(ambiguous.isEmpty(), "a JHora naksatra spanning two of ours: " + ambiguous);
    }

    /**
     * <b>Varnada Lagna: JHora does not divide it at all - it recomputes it inside each
     * divisional chart</b>, from that chart's own Lagna and Hora Lagna.
     *
     * <h2>How this was established</h2>
     * The two programs agree on <i>where</i> Varnada is to a fiftieth of an arcsecond, then place
     * it four signs apart in every varga. It is not this library's navamsa that is at fault:
     * multiplying any 14&deg;59' position by nine can only land in Leo, Taurus, Aquarius or
     * Scorpio - never in Sagittarius, which is what JHora reports. So JHora is dividing something
     * else.
     * <p>
     * It is dividing nothing. Applying the B.V. Raman Varnada construction to the <b>Lagna and
     * Hora Lagna of each varga</b> reproduces JHora's Varnada in all sixteen vargas at all
     * seventeen epochs - <b>272 of 272 signs</b> - and the degree it reports is the Lagna's own
     * degree in that chart. That is what this test asserts, using JHora's own columns on both
     * sides, so it stands as a statement about JHora rather than about this library.
     *
     * <h2>What a fix would mean</h2>
     * The machinery already exists - {@code Lagnas} computes Varnada from the Lagna and the Hora
     * Lagna - but it would have to become varga-aware, and the {@code Navamsa} column of the
     * Varnada row would then mean something different from what it means on every other row:
     * "the Varnada of the navamsa chart" rather than "the navamsa of Varnada". That is a decision
     * about what the report says, not a defect to repair, so it is recorded here rather than made.
     */
    @Test
    void jhoraRecomputesVarnadaInsideEachVargaRatherThanDividingIt() {
        int matched = 0;

        for (int year : ALL) {
            final JhoraReport jhora = JhoraReport.read(year);

            final Map<String, JhoraReport.Cell> lagna = jhora.vargas().get("LG");
            final Map<String, JhoraReport.Cell> hora = jhora.vargas().get("HL");
            final Map<String, JhoraReport.Cell> varnada = jhora.vargas().get("VL");

            assertNotNull(lagna, year + " has no Lagna row");
            assertNotNull(hora, year + " has no Hora Lagna row");
            assertNotNull(varnada, year + " has no Varnada row");

            for (String code : jhora.vargaCodes()) {
                final int expected = varnadaOf(JhoraReport.signIndex(lagna.get(code).rasi),
                        JhoraReport.signIndex(hora.get(code).rasi));

                assertEquals(expected, JhoraReport.signIndex(varnada.get(code).rasi),
                        year + " " + code + ": JHora's Varnada must be the Varnada of that chart's"
                                + " own lagna " + lagna.get(code) + " and hora " + hora.get(code));
                matched++;
            }
        }

        assertEquals(16 * ALL.length, matched, "every varga at every epoch");
    }

    /**
     * The B.V. Raman Varnada, which is the method Jagannatha Hora itself uses - written out here
     * rather than called, so this test does not check the library against itself.
     * <p>
     * Both points become an inclusive count from the same-parity end of the zodiac: an odd sign
     * counts forward from Aries, an even one backward from Pisces. The counts are added when the
     * two parities agree and subtracted when they do not, and the lagna's own parity then picks
     * the direction the result is counted in.
     */
    private static int varnadaOf(final int lagna, final int hora) {
        final int fromLagna = count(lagna), fromHora = count(hora);
        final boolean sameParity = (lagna % 2) == (hora % 2);

        int steps = (sameParity ? fromLagna + fromHora : Math.abs(fromLagna - fromHora)) % 12;
        if (0 == steps) steps = 12;

        return (lagna % 2 == 0) ? steps - 1 : 12 - steps;
    }

    /** an odd sign counts forward from Aries, an even one backward from Pisces - 1-based */
    private static int count(final int sign) {
        return (sign % 2 == 0) ? sign + 1 : 12 - sign;
    }

    /**
     * <b>The delta t difference at the ancient epochs cannot be closed, and closing it would be a
     * regression.</b>
     *
     * <h2>The measurement</h2>
     * Swiss Ephemeris offers five delta t models. Setting each in turn and asking how far JHora's
     * Moon is from ours, expressed as the offset in time it implies:
     *
     * <pre>
     * year   SM1984    S1997   SM2004   EM2006   SMH2016 (the default)
     *    0  +1322.6    +74.0    +93.6    +90.1    +47.3
     *  500  +1146.3    -44.9    -55.3    -55.3    +26.2
     * 1000    +18.0   -108.5    -78.7    -82.6    +11.3
     * 1500   +103.9   +117.1    +97.1    +98.9     +2.0
     * 1900     +0.8     +0.8     +0.8     +0.8     +0.0
     * </pre>
     *
     * The library's current default - Stephenson, Morrison and Hohenkerk 2016, which is Swiss
     * Ephemeris 2.10's too - is <b>already the closest of the five at every epoch</b>. JHora uses
     * none of them, and every alternative moves us further from JHora <i>and</i> further from the
     * modern best estimate at the same time.
     * <p>
     * The residual, some 47 seconds two millennia ago, is well inside the published uncertainty of
     * delta t at that date. This test exists so that "let us try another model to match JHora" is
     * answered by a measurement instead of an argument.
     */
    @Test
    void noOtherDeltaTModelBringsUsCloserToJagannathaHora() {
        requireEphemeris();

        final int[] models = {swisseph.SweConst.SEMOD_DELTAT_STEPHENSON_MORRISON_1984,
                swisseph.SweConst.SEMOD_DELTAT_STEPHENSON_1997,
                swisseph.SweConst.SEMOD_DELTAT_STEPHENSON_MORRISON_2004,
                swisseph.SweConst.SEMOD_DELTAT_ESPENAK_MEEUS_2006};

        for (int year : new int[]{0, 500, 1000, 1500}) {
            final double theirs = JhoraReport.read(year).points().get("CH").longitude;
            final double onDefault = apart(theirs, ours(year).row("CH").longitude);

            for (int model : models) {
                final double alternative = apart(theirs, moonUnder(year, model));

                assertTrue(onDefault < alternative, year + ": delta t model " + model
                        + " puts the Moon " + alternative + " deg from JHora, the default only "
                        + onDefault + " - the default must stay the closest of the five");
            }
        }
    }

    /**
     * The Moon under one of the alternative delta t models.
     * <p>
     * The model lives in the native library's thread-local state, so it is put back before the
     * instance is closed: leaving it set would silently change every later chart on this thread.
     */
    private double moonUnder(final int year, final int model) {
        try (ISwissEph swe = new SwephNative(Swetest.EPHE.getPath())) {
            try {
                // swephexp.h puts delta t in slot 0 - the ordering SweConst declares is a
                // different one, which is the mismatch already documented for these raw calls
                swe.swe_set_astro_models(new StringBuilder(model + ",0,0,0,0,0,0,0"), 0);

                final JhdChart chart = jhd(year);
                return new SweObjects(swe,
                        ((SweJulianDate) chart.julianDate()).calendar(SE_GREG_CAL),
                        chart.geoLocation(),
                        new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA)
                                .houseSystem(WHOLE_SIGN).trueNode(true).build())
                        .completeBuild().longitudes()[org.swisseph.api.ISweObjects.CH];
            } finally {
                swe.swe_set_astro_models(new StringBuilder("0,0,0,0,0,0,0,0"), 0);
            }
        }
    }

    // ============================================================ bhava chalit

    /**
     * <b>Bhava Chalit</b>, which JHora prints as twelve arcs with a start, a "cusp" and an end,
     * and the objects that fall in each.
     * <p>
     * Every figure is compared: all twelve starts, madhyas and ends to the arcsecond, and the
     * whole placement of the thirteen objects. This is a check on the construction rather than on
     * the ephemeris - the ascendant and the midheaven it is built from are already pinned by
     * {@link #d1LongitudesAgreeToTheArcsecond(int)} - so a disagreement here would mean the
     * quadrants are trisected differently, or that the trisection points are being read as bhava
     * beginnings rather than as middles.
     */
    @ParameterizedTest(name = "ref{0} bhava chalit")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void bhavaChalitAgreesWithJagannathaHora(final int year) {
        requireEphemeris();

        final List<JhoraReport.Chalit> theirs = JhoraReport.read(year).chalit();
        final IBhavaChalita mine = chalitOf(year);

        assertEquals(12, theirs.size(), year + " should list twelve bhavas");
        assertTrue(mine.isCalculated(), year + " must have an ascendant to build chalit from");

        for (int index = 0; index < 12; index++) {
            final IBhava bhava = EBhava.byUid(index + 1);
            final JhoraReport.Chalit row = theirs.get(index);

            assertEquals(0., apart(row.start, mine.start(bhava)), ARCSECOND,
                    year + " B" + (index + 1) + " start");
            assertEquals(0., apart(row.madhya, mine.madhya(bhava)), ARCSECOND,
                    year + " B" + (index + 1) + " madhya");
            assertEquals(0., apart(row.close, mine.close(bhava)), ARCSECOND,
                    year + " B" + (index + 1) + " close");

            final List<String> placed = new ArrayList<>();
            for (org.jyotisa.api.graha.IGraha graha : mine.grahas(bhava)) placed.add(graha.code());

            assertEquals(row.grahas, placed, year + " B" + (index + 1) + " occupants");
        }
    }

    /**
     * The lagna is the <b>madhya</b> of the first bhava, not its beginning - the single fact that
     * separates the Sripati reading from every scheme that treats a cusp as a boundary.
     */
    @Test
    void theLagnaSitsInTheMiddleOfTheFirstBhavaAndNotAtItsStart() {
        requireEphemeris();

        for (int year : MODERN) {
            final IBhavaChalita chalit = chalitOf(year);
            final IBhava first = EBhava.byUid(1);
            final double lagna = ours(year).row("LG").longitude;

            assertEquals(0., apart(lagna, chalit.madhya(first)), ARCSECOND,
                    year + ": the lagna is the madhya of B1");
            assertTrue(apart(lagna, chalit.start(first)) > 1.,
                    year + ": and is well away from where B1 begins");

            // the twelve arcs are unequal but must still cover the zodiac exactly once
            double total = 0.;
            for (int b = 1; b <= 12; b++) total += chalit.length(EBhava.byUid(b));
            assertEquals(360., total, 1e-9, year + ": the twelve arcs must sum to a full circle");
        }
    }

    /**
     * Chalit and the whole-sign bhava the rest of the report prints are two different readings,
     * and they really do disagree - if they never did, one of them would be redundant.
     */
    @Test
    void chalitAndWholeSignDisagreeSomewhere() {
        requireEphemeris();

        int differing = 0;
        for (int year : MODERN) {
            final IBhavaChalita chalit = chalitOf(year);
            final KundaliText ours = ours(year);

            for (String code : KundaliText.VARGA_COLUMNS) {
                final String wholeSign = ours.row(code).bhava.trim();
                final IBhava fromChalit = chalit.bhava(org.jyotisa.graha.EGraha.byCode(code));

                if (!wholeSign.equals(fromChalit.code())) differing++;
            }
        }

        assertTrue(differing > 0, "chalit that never differs from whole sign is not chalit");
    }

    /** a chart with no ascendant has no chalit, and says so rather than counting from zero */
    @Test
    void withoutAnAscendantThereIsNoChalit() {
        requireEphemeris();

        final ISweObjects partial = new SweObjects(swissEph(),
                ((SweJulianDate) jhd(1970).julianDate()).calendar(SE_GREG_CAL),
                jhd(1970).geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build(), false);
        partial.buildSunMoon();

        final IBhavaChalita chalit = new Kundali(KUNDALI_7_KARAKAS, partial).bhavaChalita();

        assertFalse(chalit.isCalculated(), "no ascendant, no chalit");
        assertTrue(chalit.bhava(120.).isNil(), "and no bhava to answer with");
        assertEquals(0, chalit.grahas(EBhava.byUid(1)).length, "and nothing placed in one");
    }

    private IBhavaChalita chalitOf(final int year) {
        final JhdChart chart = jhd(year);

        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(swissEph(),
                ((SweJulianDate) chart.julianDate()).calendar(SE_GREG_CAL), chart.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build()).completeBuild()).bhavaChalita();
    }

    // ============================================================ arudha padas

    /**
     * <b>The twelve Arudha Padas</b> - the perceived image each bhava casts - against JHora's own
     * D-1 column, at every one of the seventeen epochs.
     * <p>
     * An arudha is a whole sign counted from another whole sign, so there is no degree here to be
     * a fraction out: a disagreement would be a disagreement about the rule. That makes this a
     * sharper check than a longitude, and it reaches all seventeen epochs rather than only the
     * modern ten - delta t cannot move a sign here unless it moves a graha across a boundary.
     */
    @ParameterizedTest(name = "ref{0} arudha padas")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void arudhaPadasAgreeWithJagannathaHora(final int year) {
        requireEphemeris();

        final Map<String, String> theirs = JhoraReport.read(year).arudhas();
        final IArudhaPadas mine = arudhaPadasOf(year);

        assertEquals(12, theirs.size(), year + " should list twelve arudha padas");

        for (Map.Entry<String, String> entry : theirs.entrySet()) {
            final IArudhaPada pada = EArudhaPada.byName(entry.getKey());

            assertFalse(pada.isNil(), year + ": JHora names a pada this library does not: "
                    + entry.getKey());
            assertEquals(entry.getValue(), mine.rasi(pada).label(),
                    year + " " + entry.getKey());
        }
    }

    /**
     * The exception really is two cases, and both are exercised by the reference charts.
     * <p>
     * The arudha is moved to the tenth when it lands on the bhava's own rasi - which happens when
     * the lord is in the first or the seventh from it - <b>or</b> on the seventh from the bhava,
     * which happens when the lord is in the fourth or the tenth. An implementation guarding only
     * the first half passes most padas and fails a few; this asserts both halves occur in the
     * data, so the test above is genuinely testing them.
     */
    @Test
    void bothHalvesOfTheArudhaExceptionOccurInTheReferenceCharts() {
        requireEphemeris();

        int ownRasi = 0, seventhFrom = 0;

        for (int year : ALL) {
            final KundaliText ours = ours(year);
            final int lagna = rasiIndexOf(ours.row("LG").rasi) + 1;

            for (int bhava = 1; bhava <= 12; bhava++) {
                final int sign = (lagna - 1 + bhava - 1) % 12 + 1;
                final org.jyotisa.api.graha.IGraha lord = ERasi.byUid(sign).lord();
                final int at = rasiIndexOf(ours.row(lord.code()).rasi) + 1;

                final int steps = (at - sign + 12) % 12 + 1;      // inclusive distance
                final int plain = (at - 1 + steps - 1) % 12 + 1;  // before the exception

                if (plain == sign) ownRasi++;
                else if (plain == (sign - 1 + 6) % 12 + 1) seventhFrom++;
            }
        }

        assertTrue(ownRasi > 0, "no pada landed on its own bhava in seventeen charts");
        assertTrue(seventhFrom > 0, "no pada landed on the seventh from its bhava - the second"
                + " half of the exception would then be untested by the comparison above");
    }

    private static int rasiIndexOf(final String label) {
        int index = 0;
        for (String code : JhoraReport.SIGNS.values()) {
            if (code.equals(label)) return index;
            index++;
        }
        throw new AssertionError("not a rasi: " + label);
    }

    /** a chart with no ascendant has no bhavas to cast an image, and says so */
    @Test
    void withoutAnAscendantThereAreNoArudhaPadas() {
        requireEphemeris();

        final ISweObjects partial = new SweObjects(swissEph(),
                ((SweJulianDate) jhd(1970).julianDate()).calendar(SE_GREG_CAL),
                jhd(1970).geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build(), false);
        partial.buildSunMoon();

        final IArudhaPadas padas = new Kundali(KUNDALI_7_KARAKAS, partial).arudhaPadas();

        assertFalse(padas.isCalculated(), "no ascendant, no arudha padas");
        assertTrue(padas.arudhaLagna().isNil(), "and no Arudha Lagna to answer with");
        assertTrue(padas.upapadaLagna().isNil(), "nor an Upapada Lagna");
    }

    /** {@code AL} and {@code UL} are the first and the twelfth, and the accessors say so */
    @Test
    void arudhaLagnaAndUpapadaLagnaAreTheFirstAndTheTwelfth() {
        requireEphemeris();

        for (int year : MODERN) {
            final IArudhaPadas padas = arudhaPadasOf(year);

            assertEquals(padas.rasi(EArudhaPada.byUid(1)), padas.arudhaLagna(), year + " AL");
            assertEquals(padas.rasi(EArudhaPada.byUid(12)), padas.upapadaLagna(), year + " UL");

            assertEquals("AL", EArudhaPada.byUid(1).label(), "A1 is labelled AL");
            assertEquals("UL", EArudhaPada.byUid(12).label(), "A12 is labelled UL");
        }
    }

    private IArudhaPadas arudhaPadasOf(final int year) {
        final JhdChart chart = jhd(year);

        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(swissEph(),
                ((SweJulianDate) chart.julianDate()).calendar(SE_GREG_CAL), chart.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build()).completeBuild()).arudhaPadas();
    }

    /**
     * <b>Scorpio and Aquarius have two owners each, and which of the two is stronger decides two
     * of every chart's twelve padas.</b>
     * <p>
     * Whole-sign bhavas cover all twelve signs, so every chart has exactly one Scorpio bhava and
     * one Aquarius bhava. Taking the primary lord unconditionally - Mars and Saturn - is wrong for
     * 10 of the 34 such bhavas in these seventeen charts, and taking the node unconditionally is
     * wrong for 16. Neither shortcut works; the cascade in {@code ArudhaLords} is what does.
     */
    @Test
    void neitherCoLordAloneAnswersTheScorpioAndAquariusBhavas() {
        requireEphemeris();

        int primaryWrong = 0, nodeWrong = 0, both = 0, total = 0;

        for (int year : ALL) {
            final Map<String, String> theirs = JhoraReport.read(year).arudhas();
            final KundaliText ours = ours(year);
            final int lagna = ERasi.byName(ours.row("LG").rasi).fid();

            for (int bhava = 1; bhava <= 12; bhava++) {
                final int sign = (lagna - 1 + bhava - 1) % 12 + 1;
                if (8 != sign && 11 != sign) continue;      // Vrischika / Kumbha

                final String want = theirs.get(bhava == 1 ? "AL" : bhava == 12 ? "UL" : "A" + bhava);
                final String byPrimary = arudhaUnder(ours, sign, 8 == sign ? "MA" : "SA");
                final String byNode = arudhaUnder(ours, sign, 8 == sign ? "KE" : "RA");

                total++;
                if (!want.equals(byPrimary)) primaryWrong++;
                if (!want.equals(byNode)) nodeWrong++;
                if (byPrimary.equals(byNode)) both++;

                assertTrue(want.equals(byPrimary) || want.equals(byNode), year + " bhava " + bhava
                        + ": JHora's answer is neither co-lord's - the rule itself must be wrong");
            }
        }

        assertEquals(2 * ALL.length, total, "one Scorpio and one Aquarius bhava per chart");
        assertTrue(primaryWrong > 0, "the primary lord alone would do, and the cascade is pointless");
        assertTrue(nodeWrong > 0, "the node alone would do, and the cascade is pointless");
        assertTrue(both > 0, "some pairs stand in the same sign, where the choice cannot matter");
    }

    /** the arudha of a bhava, computed with a named graha forced as its lord */
    private static String arudhaUnder(final KundaliText ours, final int sign, final String lord) {
        final int at = ERasi.byName(ours.row(lord).rasi).fid();

        final int steps = (at - sign + 12) % 12 + 1;
        final int plain = (at - 1 + steps - 1) % 12 + 1;
        final int pada = (plain == sign || plain == (sign - 1 + 6) % 12 + 1)
                ? (plain - 1 + 9) % 12 + 1 : plain;

        return ERasi.byUid(pada).label();
    }

    /**
     * <b>The last rung of the cascade runs opposite to the way it is usually stated</b>, and these
     * are the two charts that say so.
     * <p>
     * When every earlier rung ties, the classical rule is "the graha further <i>advanced</i>
     * through its rasi" - which is what {@code maitreya8} and {@code PyJHora} both implement.
     * Jagannatha Hora resolves both of the cases below the other way, in favour of the <b>less</b>
     * advanced. Two observations is thin support, so they are pinned here by name: a third chart
     * that reached this rung would either confirm the reading or overturn it, and either way it
     * would be visible rather than buried.
     */
    @Test
    void theTwoChartsThatSettleTheLastRungOfTheCoLordCascade() {
        requireEphemeris();

        // 1800: the Aquarius bhava - Saturn at 12deg16' of Karkata, Rahu at 6deg33' of Mesha
        assertEquals("MIT", arudhaOfSign(1800, 11), "1800: JHora takes Rahu, the less advanced");

        // 2010: the Aquarius bhava - Saturn at 6deg15' of Kanya, Rahu at 22deg34' of Dhanus
        assertEquals("MES", arudhaOfSign(2010, 11), "2010: JHora takes Saturn, the less advanced");
    }

    /** this library's arudha for whichever bhava happens to fall in that rasi */
    private String arudhaOfSign(final int year, final int rasi) {
        final IArudhaPadas padas = arudhaPadasOf(year);
        final int lagna = ERasi.byName(ours(year).row("LG").rasi).fid();

        return padas.rasi(EArudhaPada.byUid((rasi - lagna + 12) % 12 + 1)).label();
    }

    // ============================================================ avasthas

    /**
     * <b>The age avastha of every graha</b> - JHora's first "Основні авастхи" column - at all
     * seventeen epochs.
     * <p>
     * Five parts of 6&deg; to a sign, forward through an odd sign and backward through an even
     * one. A 6&deg; band is coarse enough that delta t cannot move a graha across it at these
     * epochs, so this reaches the ancient charts as well as the modern ones.
     * <p>
     * Only the age column is compared. JHora prints three more families beside it - wakefulness,
     * mood and the twelve-fold shayanadi activity - and none of the three is implemented here:
     * the mood is a <i>set</i> per graha rather than one value, and the other two need the
     * dignity and a further computation.
     */
    @ParameterizedTest(name = "ref{0} avasthas")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800, 1900,
            1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void avasthasAgreeWithJagannathaHora(final int year) {
        requireEphemeris();

        final Map<String, String> theirs = JhoraReport.read(year).avasthas();
        final KundaliText ours = ours(year);

        assertEquals(9, theirs.size(), year + " should list the seven grahas and both nodes");

        for (Map.Entry<String, String> entry : theirs.entrySet()) {
            final double longitude = ours.row(entry.getKey()).longitude;

            assertEquals(entry.getValue(), EAvastha.byLongitude(longitude).code(),
                    year + " " + entry.getKey() + " at " + longitude);
        }
    }

    /**
     * The reversal is what makes this more than a fifth-of-a-sign lookup, so it is pinned
     * directly: the same degree gives opposite avasthas in an odd and an even sign.
     */
    @Test
    void theAvasthaRunsBackwardThroughAnEvenSign() {
        // Mesha is the first sign and so an odd one, Vrishabha the second and even
        assertEquals("AV1", EAvastha.byLongitude(1.).code(), "the start of an odd sign is the infant");
        assertEquals("AV5", EAvastha.byLongitude(29.).code(), "and its end is the dead");

        assertEquals("AV5", EAvastha.byLongitude(31.).code(), "the start of an even sign is the dead");
        assertEquals("AV1", EAvastha.byLongitude(59.).code(), "and its end is the infant");

        // the middle band is the same either way, being the middle
        assertEquals("AV3", EAvastha.byLongitude(15.).code());
        assertEquals("AV3", EAvastha.byLongitude(45.).code());

        assertTrue(EAvastha.byLongitude(Double.NaN).isNil(), "an unknown longitude names no avastha");
    }

    // ============================================================ the basics

    @ParameterizedTest(name = "ref{0} panchanga")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void panchangaAgrees(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        // JHora prints what remains of each element, this library what has elapsed
        assertEquals(100., jhora.tithiRemaining() + elapsed(year, "Tithi"), 0.02, year + " tithi");
        assertEquals(100., jhora.naksatraRemaining() + elapsed(year, "Naksatra"), 0.02, year + " naksatra");
        assertEquals(100., jhora.yogaRemaining() + elapsed(year, "Nitya Yoga"), 0.02, year + " yoga");
        assertEquals(100., jhora.karanaRemaining() + elapsed(year, "Karana"), 0.02, year + " karana");

        // and the graha that rules each of them, which JHora prints in the same line
        assertEquals(jhora.naksatraLord(), ours.row("CH").naksatraLord, year + " naksatra lord");
    }

    /**
     * How much of a panchanga element has <b>elapsed</b>, from {@code "Tithi: K14(14.28%)"}.
     * <p>
     * Read from the report text rather than from {@link KundaliText}, which keeps the code and
     * discards the percentage - and the percentage is the half that can be compared here, since
     * JHora names its elements in Ukrainian.
     */
    private double elapsed(final int year, final String key) {
        for (String line : report(year).split("\\R")) {
            if (!line.startsWith("Housesys:")) continue;

            for (String part : line.split(",")) {
                final int colon = part.indexOf(':');
                if (colon < 0 || !part.substring(0, colon).trim().equals(key)) continue;

                final String value = part.substring(colon + 1);
                return Double.parseDouble(
                        value.substring(value.indexOf('(') + 1, value.indexOf('%')).trim());
            }
        }
        throw new AssertionError("no '" + key + "' in the report for " + year);
    }

    @ParameterizedTest(name = "ref{0} sidereal time")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void siderealTimeAgrees(final int year) {
        requireEphemeris();

        final String theirs = JhoraReport.read(year).siderealTime();
        final String mine = fieldOf(year, "Sidereal Time");

        assertEquals(normalizeClock(theirs), normalizeClock(mine), year + " sidereal time");
    }

    /**
     * Sunrise and sunset, shifted by the chart's own time zone: JHora prints them local, this
     * library in UTC. Agreement here is a check on the rise/set flags as much as on the ephemeris
     * - a disc-centre or refraction difference would show as seconds.
     */
    @ParameterizedTest(name = "ref{0} sunrise and sunset")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void sunriseAndSunsetAgree(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final double zone = jhd(year).julianDate().timeZone();

        assertEquals(seconds(jhora.sunrise()),
                seconds(clockOf(year, "UTC Sunrise")) + Math.round(zone * 3600.), 1,
                year + " sunrise");
        assertEquals(seconds(jhora.sunset()),
                seconds(clockOf(year, "UTC Sunset")) + Math.round(zone * 3600.), 1,
                year + " sunset");
    }

    /**
     * The one number in the basics that differs on purpose: JHora reports the ayanamsa
     * <b>without</b> nutation in longitude, this library with it. The gap must therefore be
     * exactly that nutation, which is read from Swiss Ephemeris rather than assumed.
     */
    @ParameterizedTest(name = "ref{0} ayanamsa")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void theAyanamsaDiffersByNutationAlone(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        final double gap = (ours.ayanamsa() - jhora.ayanamsa()) * 3600.;
        final double nutation = nutationInLongitude(year) * 3600.;

        assertEquals(nutation, gap, 0.05, year + " ayanamsa gap should be the nutation:"
                + " ours " + ours.ayanamsa() + " jhora " + jhora.ayanamsa());
    }

    private double nutationInLongitude(final int year) {
        final JhdChart jhd = jhd(year);
        final ISweObjects objects = new SweObjects(swissEph(),
                ((SweJulianDate) jhd.julianDate()).calendar(SE_GREG_CAL), jhd.geoLocation(),
                new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                        .trueNode(true).build()).completeBuild();

        final double[] xx = new double[6];
        final StringBuilder serr = new StringBuilder();
        final double jdET = objects.sweJulianDate().julianDay()
                + objects.sweJulianDate().deltaT();

        swissEph().swe_calc(jdET, swisseph.SweConst.SE_ECL_NUT,
                objects.sweOptions().mainFlags() & swisseph.SweConst.SEFLG_EPHMASK, xx, serr);
        return xx[2];
    }

    // ============================================================ the ashtakavarga

    /**
     * The eight Bhinnashtakavarga rows agree cell for cell - <b>except two cells of the Moon's
     * own row</b>, at sixteen of the seventeen epochs.
     * <p>
     * The row total is identical every time, and it is the classical 49, so neither program is
     * simply wrong: one bindu sits in a different sign. Both tables are self-consistent - this
     * library's {@code REKHA_MAP} is checked against all seven classical per-graha totals - so
     * this is a difference between two published tables rather than an arithmetic error, and
     * choosing between them is an author's decision. It is pinned here, precisely, so that the
     * day one of them changes it is visible.
     */
    @Test
    void ashtakavargaAgreesExceptTwoCellsOfTheMoonsOwnRow() {
        requireEphemeris();

        int cells = 0, differing = 0;
        final Set<String> rowsThatDiffer = new LinkedHashSet<>();

        for (int year : ALL) {
            final JhoraReport jhora = JhoraReport.read(year);
            final KundaliText ours = ours(year);
            final org.jyotisa.api.varga.IAshtakavarga av = null;   // read from the report instead

            for (Map.Entry<String, int[]> row : jhora.bav().entrySet()) {
                final int[] mine = bavRow(year, row.getKey());
                if (null == mine) continue;

                assertEquals(sum(row.getValue()), sum(mine),
                        year + " " + row.getKey() + " row total");

                for (int i = 0; i < 12; i++) {
                    cells++;
                    if (row.getValue()[i] != mine[i]) {
                        differing++;
                        rowsThatDiffer.add(row.getKey());
                    }
                }
            }
        }

        assertTrue(cells > 1500, "expected eight rows at every epoch, compared " + cells);
        assertEquals(new LinkedHashSet<>(java.util.Collections.singletonList("CH")), rowsThatDiffer,
                "only the Moon's own Bhinnashtakavarga is expected to differ");
        assertTrue(differing <= 2 * ALL.length, "at most two cells per epoch, saw " + differing);
    }

    private static int sum(final int[] values) {
        int total = 0;
        for (int value : values) total += value;
        return total;
    }

    // ============================================================ the ancient epochs

    /**
     * Before 1900 the two programs drift, and this pins <b>why</b> rather than loosening a
     * tolerance over it.
     * <p>
     * Two independent bodies imply the same offset in time - the Sun moves 0.041"/s and the Moon
     * 0.605"/s, and dividing each divergence by its own speed gives the same number of seconds -
     * which is what a delta t difference looks like and what nothing else does. Sree Lagna
     * amplifies the Moon's naksatra progress by 27, and its divergence is 27 times the Moon's to
     * two decimal places. The whole effect decays to nothing by 1900, as delta t models converge
     * on measured values.
     */
    @Test
    void theAncientEpochsDivergeThroughDeltaTAndNothingElse() {
        requireEphemeris();

        final double sunPerSecond = 0.0411, moonPerSecond = 0.605;   // arcsec/s, near enough
        double previous = Double.MAX_VALUE;

        for (int year : new int[]{0, 100, 500, 1000, 1500, 1700, 1800, 1900}) {
            final JhoraReport jhora = JhoraReport.read(year);
            final KundaliText ours = ours(year);

            final double sun = apart(jhora.points().get("SY").longitude, ours.row("SY").longitude) * 3600.;
            final double moon = apart(jhora.points().get("CH").longitude, ours.row("CH").longitude) * 3600.;
            final double sree = apart(jhora.points().get("SL").longitude, ours.row("SL").longitude) * 3600.;

            if (moon > 1.) {
                assertEquals(27., sree / moon, 0.5, year
                        + ": Sree Lagna must be 27x the Moon, being 27x its naksatra progress");
                assertEquals(sun / sunPerSecond, moon / moonPerSecond, 8., year
                        + ": the Sun and the Moon must imply the same offset in time");
            }

            assertTrue(moon <= previous + 0.01, year
                    + ": the divergence must shrink towards the present, was " + previous
                    + " now " + moon);
            previous = moon;
        }

        assertTrue(previous < 0.1, "by 1900 the models have converged, still " + previous + "\"");
    }

    /** the signs still agree even at the ancient epochs - the drift is far below a sign */
    @ParameterizedTest(name = "ref{0} signs")
    @ValueSource(ints = {0, 100, 500, 1000, 1500, 1700, 1800})
    void theAncientEpochsStillAgreeOnEverySign(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        for (JhoraReport.Point point : jhora.points().values()) {
            if (!ours.has(point.code)) continue;
            assertEquals(point.rasi, ours.row(point.code).rasi, year + " " + point.code);
        }
    }

    // ============================================================ the chara karakas

    @ParameterizedTest(name = "ref{0} chara karakas")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void charaKarakasAgree(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        int compared = 0;
        for (JhoraReport.Point point : jhora.points().values()) {
            if (null == point.karaka || !ours.has(point.code)) continue;

            assertEquals(point.karaka, ours.row(point.code).karaka.trim(),
                    year + " " + point.code + " chara karaka");
            compared++;
        }

        assertEquals(7, compared, "the seven-karaka scheme assigns exactly seven");
    }

    @ParameterizedTest(name = "ref{0} retrogrades")
    @ValueSource(ints = {1900, 1970, 1990, 2000, 2010, 2030, 2050, 2070, 2090, 2100})
    void retrogradeFlagsAgree(final int year) {
        requireEphemeris();

        final JhoraReport jhora = JhoraReport.read(year);
        final KundaliText ours = ours(year);

        for (JhoraReport.Point point : jhora.points().values()) {
            if (!ours.has(point.code)) continue;

            // the lunar nodes are always retrograde and this library does not mark them as such
            if ("RA".equals(point.code) || "KE".equals(point.code)) continue;

            assertEquals(point.retrograde, ours.row(point.code).retrograde,
                    year + " " + point.code + " retrograde");
        }
    }

    // ============================================================ report plumbing

    /** a line of the fields block, "Sidereal Time\t: 06:34:49" */
    private String fieldOf(final int year, final String key) {
        for (String line : report(year).split("\\R")) {
            if (line.trim().startsWith(key)) return line.substring(line.indexOf(':') + 1).trim();
        }
        throw new AssertionError("no '" + key + "' in the report for " + year);
    }

    /** the clock part of "UTC Sunrise\t: 1970-04-04 00:32:22" */
    private String clockOf(final int year, final String key) {
        final String value = fieldOf(year, key);
        return value.substring(value.lastIndexOf(' ') + 1);
    }

    private String reportCache;
    private int reportYear = Integer.MIN_VALUE;

    private String report(final int year) {
        if (year != reportYear) {
            final JhdChart jhd = jhd(year);
            reportCache = new Kundali(KUNDALI_7_KARAKAS, new SweObjects(swissEph(),
                    ((SweJulianDate) jhd.julianDate()).calendar(SE_GREG_CAL), jhd.geoLocation(),
                    new SweObjectsOptions.Builder().ayanamsa(TRUE_CITRA).houseSystem(WHOLE_SIGN)
                            .trueNode(true).build()).completeBuild()).toString();
            reportYear = year;
        }
        return reportCache;
    }

    /** the Bhinnashtakavarga row this library prints, or null if it prints none */
    private int[] bavRow(final int year, final String code) {
        for (String line : report(year).split("\\R")) {
            final String[] parts = line.trim().split("\\s+");
            if (parts.length != 13 || !parts[0].equals(code)) continue;

            final int[] values = new int[12];
            for (int i = 0; i < 12; i++) {
                if (!parts[i + 1].matches("\\d+")) return null;
                values[i] = Integer.parseInt(parts[i + 1]);
            }
            return values;
        }
        return null;
    }

    private static String normalizeClock(final String clock) {
        final String[] parts = clock.split(":");
        return String.format("%02d:%02d:%02d", Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    private static long seconds(final String clock) {
        final String[] parts = clock.split(":");
        return Integer.parseInt(parts[0]) * 3600L
                + Integer.parseInt(parts[1]) * 60L + Integer.parseInt(parts[2]);
    }

    /** the seventeen files are all there, and all parse */
    @Test
    void everyReferenceFileIsReadable() {
        for (int year : ALL) {
            final JhoraReport jhora = JhoraReport.read(year);

            assertFalse(jhora.points().isEmpty(), year + " has no objects");
            assertEquals(16, jhora.vargaCodes().size(), year + " should list sixteen vargas");
            assertEquals(8, jhora.bav().size(), year + " should list eight ashtakavarga rows");
            assertTrue(jhora.ayanamsa() > 0., year + " has no ayanamsa");
        }
    }
}
