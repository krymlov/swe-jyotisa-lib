/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.app.Kundali;
import org.jyotisa.gochara.ChayaGrahaGochara;
import org.jyotisa.gochara.ChayaGrahaGocharaSpecial;
import org.jyotisa.gochara.GrahaGochara;
import org.jyotisa.gochara.GrahaGocharaSpecial;
import org.jyotisa.gochara.GrahaStationGochara;
import org.jyotisa.gochara.naksatra.NaksatraChayaGrahaGochara;
import org.jyotisa.gochara.naksatra.NaksatraGrahaGochara;
import org.jyotisa.gochara.naksatra.NaksatraPadaChayaGrahaGochara;
import org.jyotisa.gochara.naksatra.NaksatraPadaGrahaGochara;
import org.jyotisa.gochara.rasi.RasiChayaGrahaGochara;
import org.jyotisa.gochara.rasi.RasiGrahaGochara;
import org.jyotisa.gochara.rasi.RasiLagnaGochara;
import org.jyotisa.karana.KaranaIterator;
import org.jyotisa.nityayoga.NityaYogaIterator;
import org.jyotisa.tithi.TithiIterator;
import org.swisseph.api.ISweEnumEntity;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;

import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.jyotisa.gochara.GrahaStationGochara.Stations.ANY;
import static org.jyotisa.gochara.GrahaStationGochara.Stations.DIRECT;
import static org.jyotisa.gochara.GrahaStationGochara.Stations.RETROGRADE;
import static org.jyotisa.graha.EGraha.*;
import static org.jyotisa.refbase.GocharaReference.assertMatchesReference;
import static org.jyotisa.refbase.GocharaReference.collect;
import static org.swisseph.api.ISweObjects.LG;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * A reference base for every transit iterator in this library: each one run 100 records forward
 * and 100 backward from <b>2025-01-01 00:00 UT</b>, held against a checked-in file of
 *
 * <pre>
 * CODE | 95&deg;42'57.23" | 2025-03-27 05:40:57.89
 * </pre>
 *
 * <h2>What this covers</h2>
 * Every concrete class that extends {@code KundaliIterator}, which includes all the
 * {@code KundaliSequenceIterator} ones:
 * <pre>
 * KundaliSequenceIterator   TithiIterator, KaranaIterator, NityaYogaIterator,
 *                           RasiGrahaGochara, RasiChayaGrahaGochara, RasiLagnaGochara,
 *                           NaksatraGrahaGochara, NaksatraChayaGrahaGochara,
 *                           NaksatraPadaGrahaGochara, NaksatraPadaChayaGrahaGochara
 * KundaliIterator           GrahaGochara, ChayaGrahaGochara, GrahaGocharaSpecial,
 *                           ChayaGrahaGocharaSpecial, GrahaStationGochara
 * </pre>
 * {@code ShaniGochara} is deliberately absent - see {@link #shaniGocharaIsNotPartOfThisBase()}.
 * <p>
 * Where a class takes a graha, both a fast one (Chandra) and a slow one (Guru) are used where
 * that exercises different stepping, and the chaya variants use Rahu - Ketu is its mirror
 * 180&deg; away and adds no independent path.
 *
 * <h2>Why these files are worth having</h2>
 * The transit search is shared machinery: {@code KundaliIterator} drives every one of these
 * classes through the same {@code TransitCalculator}, so a change to the stepping, to the
 * alignment, or to delta t moves <b>all</b> of them at once. Until now nothing pinned that -
 * the existing iterator tests re-derive the expected sequence from the same code they are
 * testing, which cannot see a shared-machinery shift. These files can.
 * <p>
 * They are <b>golden masters</b>: a passing run never rewrites them. On a mismatch the actual
 * output goes to the OS temp directory under the same relative path, so an intended change is a
 * diff and a copy away.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class GocharaReferenceTest extends AbstractTest {

    /** 2025-01-01 00:00:00 UT */
    private static final double Y2025 = 2460676.5;

    /**
     * One fixed chart for every file: same instant, same place, same options, so the only thing
     * that varies between files is the iterator under test.
     *
     * <h2>Built fresh every time, and that is not wasteful caution</h2>
     * {@link AbstractTest} is {@code @TestInstance(PER_CLASS)} and {@code @Execution(CONCURRENT)},
     * so one instance field shared by these methods is shared <b>across threads</b>. A
     * {@code Kundali} keeps the {@code ISwissEph} it was built with, {@code KundaliIterator} takes
     * that same instance, and the native library's {@code swed} - which holds the ayanamsa set by
     * {@code swe_set_sid_mode} and the observer set by {@code swe_set_topo} - is <b>thread-local</b>.
     * <p>
     * Handing such a chart to another thread therefore computes against a {@code swed} nobody ever
     * configured: the ayanamsa silently reverts to the default Fagan/Bradley. Caching the chart in
     * a field made this suite fail with every forward file off by <b>0&deg;53'57"</b> while the
     * moments stayed right - no exception, no warning, just a different zodiac. Each method now
     * builds its own chart with its own thread's {@code ISwissEph}.
     */
    private IKundali kundali() {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(getSwephExp(),
                new SweJulianDate(Y2025), GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    private <E extends ISweEnumEntity<?>> void reference(
            final String name, final Iterator<E> iterator, final Function<E, String> codeOf)
            throws IOException {
        reference(name, iterator, codeOf, GocharaReference.RECORDS);
    }

    private <E extends ISweEnumEntity<?>> void reference(
            final String name, final Iterator<E> iterator,
            final Function<E, String> codeOf, final int records) throws IOException {
        assertMatchesReference(name, collect(getSwephExp(), iterator, codeOf, records));
    }

    /** the plain case: the entity's own member code */
    private static String code(final ISweEnumEntity<?> entity) {
        return entity.entityEnum().code();
    }

    /** a graha crossing a segment boundary - both halves are needed to read the record */
    private static String code(final IGraha graha, final ISweEnumEntity<?> entity) {
        return graha.code() + "-" + entity.entityEnum().code();
    }

    // ==================================================================== panchanga sequences

    @Test
    void tithiIterator() throws IOException {
        reference("tithi-forward", new TithiIterator(kundali(), true), GocharaReferenceTest::code);
        reference("tithi-backward", new TithiIterator(kundali(), false), GocharaReferenceTest::code);
    }

    @Test
    void karanaIterator() throws IOException {
        reference("karana-forward", new KaranaIterator(kundali(), true), GocharaReferenceTest::code);
        reference("karana-backward", new KaranaIterator(kundali(), false), GocharaReferenceTest::code);
    }

    @Test
    void nityaYogaIterator() throws IOException {
        reference("nityayoga-forward", new NityaYogaIterator(kundali(), true), GocharaReferenceTest::code);
        reference("nityayoga-backward", new NityaYogaIterator(kundali(), false), GocharaReferenceTest::code);
    }

    // ==================================================================== rasi boundaries

    @Test
    void rasiGrahaGochara() throws IOException {
        for (IGraha graha : new IGraha[]{CHANDRA.graha(), GURU.graha()}) {
            reference("rasi-graha-" + graha.code() + "-forward",
                    new RasiGrahaGochara(kundali(), graha, true), e -> code(graha, e));
            reference("rasi-graha-" + graha.code() + "-backward",
                    new RasiGrahaGochara(kundali(), graha, false), e -> code(graha, e));
        }
    }

    @Test
    void rasiChayaGrahaGochara() throws IOException {
        reference("rasi-chaya-RA-forward",
                new RasiChayaGrahaGochara(kundali(), true, true), GocharaReferenceTest::code);
        reference("rasi-chaya-RA-backward",
                new RasiChayaGrahaGochara(kundali(), true, false), GocharaReferenceTest::code);
    }

    @Test
    void rasiLagnaGochara() throws IOException {
        reference("rasi-lagna-forward",
                new RasiLagnaGochara(kundali(), true), GocharaReferenceTest::code);
        reference("rasi-lagna-backward",
                new RasiLagnaGochara(kundali(), false), GocharaReferenceTest::code);
    }

    // ==================================================================== naksatra boundaries

    @Test
    void naksatraGrahaGochara() throws IOException {
        for (IGraha graha : new IGraha[]{CHANDRA.graha(), GURU.graha()}) {
            reference("naksatra-graha-" + graha.code() + "-forward",
                    new NaksatraGrahaGochara(kundali(), graha, true), e -> code(graha, e));
            reference("naksatra-graha-" + graha.code() + "-backward",
                    new NaksatraGrahaGochara(kundali(), graha, false), e -> code(graha, e));
        }
    }

    @Test
    void naksatraChayaGrahaGochara() throws IOException {
        reference("naksatra-chaya-RA-forward",
                new NaksatraChayaGrahaGochara(kundali(), true, true), GocharaReferenceTest::code);
        reference("naksatra-chaya-RA-backward",
                new NaksatraChayaGrahaGochara(kundali(), true, false), GocharaReferenceTest::code);
    }

    @Test
    void naksatraPadaGrahaGochara() throws IOException {
        final IGraha graha = CHANDRA.graha();
        reference("naksatrapada-graha-CH-forward",
                new NaksatraPadaGrahaGochara(kundali(), graha, true), e -> code(graha, e));
        reference("naksatrapada-graha-CH-backward",
                new NaksatraPadaGrahaGochara(kundali(), graha, false), e -> code(graha, e));
    }

    @Test
    void naksatraPadaChayaGrahaGochara() throws IOException {
        reference("naksatrapada-chaya-RA-forward",
                new NaksatraPadaChayaGrahaGochara(kundali(), true, true), GocharaReferenceTest::code);
        reference("naksatrapada-chaya-RA-backward",
                new NaksatraPadaChayaGrahaGochara(kundali(), true, false), GocharaReferenceTest::code);
    }

    // ==================================================================== degree steps

    @Test
    void grahaGochara() throws IOException {
        for (IGraha graha : new IGraha[]{CHANDRA.graha(), GURU.graha()}) {
            reference("graha-" + graha.code() + "-forward",
                    new GrahaGochara(kundali(), graha, true), GocharaReferenceTest::code);
            reference("graha-" + graha.code() + "-backward",
                    new GrahaGochara(kundali(), graha, false), GocharaReferenceTest::code);
        }
    }

    @Test
    void chayaGrahaGochara() throws IOException {
        reference("chaya-RA-forward",
                new ChayaGrahaGochara(kundali(), true, 1., true), GocharaReferenceTest::code);
        reference("chaya-RA-backward",
                new ChayaGrahaGochara(kundali(), true, 1., false), GocharaReferenceTest::code);
    }

    // ==================================================================== a fixed longitude

    /**
     * These two return the graha to <b>one</b> degree, so a record is a whole synodic return -
     * about 11.9 years for Guru and 18.6 for Rahu. A hundred of those is seven centuries, well
     * past the year {@link GocharaReference#EPHE_LAST_YEAR} this project's {@code ephe/} covers,
     * so the counts are cut to what stays on real ephemeris data.
     * <p>
     * That limit is not a formality: the first generated pass ran Guru out to <b>2729</b> and
     * Swiss Ephemeris answered anyway, from Moshier, with nothing but a warning. The guard in
     * {@code line()} is what turned that into a failure.
     */
    private static final int GURU_RETURN_RECORDS = 24;

    /**
     * Rahu is slower still, and its failure at the boundary is not the usual quiet one: with a
     * star-derived sidereal ayanamsa the Moshier fallback cannot compute {@code SE_TRUE_NODE} at
     * all - the documented upstream defect - so it comes back as a bare
     * {@code SwissephException} with return code -1 rather than as a plausible wrong number.
     */
    private static final int RAHU_RETURN_RECORDS = 16;

    /** the natal Lagna degree - a real point of the chart rather than an arbitrary number */
    private double natalLagna() {
        return kundali().sweObjects().longitudes()[LG];
    }

    @Test
    void grahaGocharaSpecial() throws IOException {
        reference("special-graha-GU-forward",
                new GrahaGocharaSpecial(kundali(), natalLagna(), GURU.graha(), true),
                GocharaReferenceTest::code, GURU_RETURN_RECORDS);
        reference("special-graha-GU-backward",
                new GrahaGocharaSpecial(kundali(), natalLagna(), GURU.graha(), false),
                GocharaReferenceTest::code, GURU_RETURN_RECORDS);
    }

    @Test
    void chayaGrahaGocharaSpecial() throws IOException {
        // Rahu only - the class has no graha to choose, it is the chaya graha by construction
        reference("special-chaya-RA-forward",
                new ChayaGrahaGocharaSpecial(kundali(), true, natalLagna(), true),
                GocharaReferenceTest::code, RAHU_RETURN_RECORDS);
        reference("special-chaya-RA-backward",
                new ChayaGrahaGocharaSpecial(kundali(), true, natalLagna(), false),
                GocharaReferenceTest::code, RAHU_RETURN_RECORDS);
    }

    // ==================================================================== stations

    @Test
    void grahaStationGocharaBothKinds() throws IOException {
        for (IGraha graha : new IGraha[]{BUDHA.graha(), GURU.graha()}) {
            reference("station-" + graha.code() + "-forward",
                    new GrahaStationGochara(kundali(), graha, true, ANY), GocharaReferenceTest::code);
            reference("station-" + graha.code() + "-backward",
                    new GrahaStationGochara(kundali(), graha, false, ANY), GocharaReferenceTest::code);
        }
    }

    @Test
    void grahaStationGocharaFiltered() throws IOException {
        final IGraha graha = BUDHA.graha();
        reference("station-BU-retrograde-forward",
                new GrahaStationGochara(kundali(), graha, true, RETROGRADE), GocharaReferenceTest::code);
        reference("station-BU-direct-forward",
                new GrahaStationGochara(kundali(), graha, true, DIRECT), GocharaReferenceTest::code);
    }

    // ==================================================================== what is left out

    /**
     * {@code ShaniGochara} extends {@code KundaliIterator} but cannot join this base, and the
     * reason is worth recording rather than leaving as an omission.
     * <p>
     * It does not use a {@code TransitCalculator} at all - {@code createTransitCalc()} throws
     * {@code NotImplementedException} - and instead shells out to {@code swisseph.Transits} with
     * a {@code swetest}-style argument string, then parses the captured stdout. It is also
     * forward-only by construction and fixed to a 120-year window around the birth year, so
     * "100 records from 2025 in both directions" is not something it can answer.
     * <p>
     * That is the "refused bequest" the API review flagged: a subclass that inherits a contract
     * it cannot honour. Pinning the gap here means the day it is rewritten on top of
     * {@code TransitCalculator}, this test is the reminder to add it to the base.
     */
    @Test
    void shaniGocharaIsNotPartOfThisBase() {
        // documentation-only; nothing to assert beyond the fact recorded above
    }
}
