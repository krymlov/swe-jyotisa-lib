/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.app;

import org.junit.jupiter.api.Test;
import org.jyotisa.AbstractTest;
import org.jyotisa.api.IKundali;
import org.jyotisa.gochara.rasi.RasiLagnaGochara;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.api.ISweConstants.EPHE_PATH;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;

/**
 * <b>A built {@link IKundali} belongs to the thread that built it.</b>
 * <p>
 * This is not a defect being reported - it is a consequence of the native library that nothing
 * states, and that fails <i>silently and plausibly</i> rather than loudly. It is pinned here so
 * it is a known property rather than a discovery.
 *
 * <h2>Why</h2>
 * A {@code Kundali} keeps the {@code ISwissEph} it was built with, and
 * {@link org.jyotisa.app.KundaliIterator} takes that same instance
 * ({@code this.swissEph = kundali.swissEph()}). The native library's whole state - {@code swed} -
 * is <b>thread-local</b>, and that state is where {@code swe_set_sid_mode} puts the ayanamsa and
 * {@code swe_set_topo} the observer. {@code SweObjects} sets both when the chart is built, on the
 * building thread.
 * <p>
 * Use that chart from a second thread and every call reaches a {@code swed} nobody configured: the
 * ayanamsa reverts to the library default (Fagan/Bradley) and the observer to none. Nothing
 * throws. The numbers stay entirely plausible - they are simply computed in a different zodiac,
 * about 0.9&deg; away in 2025.
 *
 * <h2>How to use a chart on another thread</h2>
 * Rebuild it there, or give that thread its own {@code ISwissEph} first - constructing a
 * {@code SweObjects} on it is enough, since that is what calls {@code initSwissEph}. What does not
 * work is passing the object across and hoping.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see org.swisseph.api.ISweObjects#initSwissEph(ISwissEph, org.swisseph.api.ISweGeoLocation, org.swisseph.api.ISweObjectsOptions)
 */
class KundaliThreadAffinityTest extends AbstractTest {

    /** 2025-01-01 00:00 UT */
    private static final double Y2025 = 2460676.5;

    private IKundali buildWith(final ISwissEph swissEph) {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(swissEph,
                new SweJulianDate(Y2025), GEO_KYIV, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild());
    }

    /** the moment the sidereal ascendant next crosses a rasi boundary - sensitive to both */
    private static double firstLagnaCrossing(final IKundali kundali) {
        return new RasiLagnaGochara(kundali, true).next().julianDay();
    }

    private static <T> T onAnotherThread(final java.util.concurrent.Callable<T> task) throws Exception {
        final ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            final Future<T> result = pool.submit(task);
            return result.get();
        } finally {
            pool.shutdown();
        }
    }

    /**
     * The trap itself: same chart object, same iterator, two threads, two different answers - and
     * no exception on either.
     * <p>
     * Five minutes of ascendant is more than a whole rasi boundary is worth arguing about, and it
     * is the exact size of the discrepancy that this property produced in
     * {@code GocharaReferenceTest} before its chart stopped being cached in a shared field.
     */
    @Test
    void aChartUsedFromAnotherThreadSilentlyAnswersInADifferentZodiac() throws Exception {
        final IKundali kundali = buildWith(getSwephExp());

        final double here = firstLagnaCrossing(kundali);
        final double there = onAnotherThread(() -> firstLagnaCrossing(kundali));

        final double minutes = Math.abs(there - here) * 1440.;
        assertTrue(minutes > 1., "the cross-thread answer is expected to differ by minutes,"
                + " not to agree - if this now passes, the native state stopped being thread-local"
                + " and this whole test can go; measured " + minutes + " minutes");
    }

    /**
     * And the remedy: give the other thread an {@code ISwissEph} of its own and build the chart
     * there. The two answers then agree exactly, which is what proves the difference above is the
     * thread-local state and not something about the chart or the iterator.
     */
    @Test
    void rebuildingTheChartOnThatThreadRestoresTheChartsOwnZodiac() throws Exception {
        final double here = firstLagnaCrossing(buildWith(getSwephExp()));

        final double there = onAnotherThread(() -> {
            try (ISwissEph ownSwissEph = new SwephNative(EPHE_PATH)) {
                return firstLagnaCrossing(buildWith(ownSwissEph));
            }
        });

        assertEquals(here, there, 0., "a chart built on the thread that uses it must agree exactly");
    }
}
