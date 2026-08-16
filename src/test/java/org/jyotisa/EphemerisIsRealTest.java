/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa;

import org.junit.jupiter.api.Test;
import org.swisseph.ISwissEph;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.swisseph.api.ISweConstants.EPHE_PATH;
import static swisseph.SweConst.*;

/**
 * Guards against the whole suite silently degrading to Moshier.
 * <p>
 * {@code EPHE_PATH} is the relative path {@code "ephe"}, resolved against each module's own
 * working directory, so a module simply without that folder still runs green - the native
 * engine falls back to Moshier without a word. That is not hypothetical: {@code swe-jyotisa-meta}
 * and {@code jyotisa-support} were both in exactly that state until 2026-08-16 - no
 * {@code ephe/} folder at all, every chart computed at Moshier precision (measured: ~0.3" on the
 * Moon, ~14" on the true node) while every one of their tests passed green.
 * <p>
 * The check does not merely look for the files - it asks Swiss Ephemeris which ephemeris it
 * actually used. {@code swe_calc} returns the engine it settled on in its result flags, so
 * {@code SEFLG_SWIEPH} present and {@code SEFLG_MOSEPH} absent is proof the {@code .se1} files
 * were really read, not just present on disk.
 */
class EphemerisIsRealTest extends AbstractTest {

    /** 2026-08-16 12:00 TT - any epoch the shipped sepl/semo_18 files cover */
    private static final double JD = 2461269.0;

    @Test
    void epheFolderShipsTheSwissEphemerisFiles() {
        final File ephe = new File(EPHE_PATH);
        assertTrue(ephe.isDirectory(), "missing ephemeris folder: " + ephe.getAbsolutePath()
                + " - without it every chart silently drops to Moshier precision");
        for (String required : new String[]{"sepl_18.se1", "semo_18.se1", "sefstars.txt"}) {
            assertTrue(new File(ephe, required).isFile(), "missing " + required + " in " + ephe.getAbsolutePath());
        }
    }

    @Test
    void sweCalcActuallyReadsTheSwissEphemerisFilesRatherThanFallingBackToMoshier() {
        final ISwissEph swe = getSwephExp();

        for (int body : new int[]{SE_SUN, SE_MOON, SE_MARS, SE_TRUE_NODE}) {
            final double[] xx = new double[6];
            final StringBuilder serr = new StringBuilder();
            final int rc = swe.swe_calc(JD, body, SEFLG_SWIEPH | SEFLG_SPEED, xx, serr);

            assertTrue(rc >= 0, "swe_calc failed for body " + body + ": " + serr);
            assertEquals(SEFLG_SWIEPH, rc & SEFLG_SWIEPH,
                    "body " + body + " was NOT computed from the .se1 files (rc=" + rc + ")");
            assertEquals(0, rc & SEFLG_MOSEPH,
                    "body " + body + " silently fell back to Moshier (rc=" + rc + ")");
        }
    }
}
