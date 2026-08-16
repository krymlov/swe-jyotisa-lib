/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.ashtakavarga;

import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.graha.EGraha;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweObjects;

import static org.swisseph.api.ISweObjects.*;

/**
 * Bhinnashtakavarga (each of the 7 classical grahas' and Lagna's own 12-rasi "bindu" table) and
 * Sarvashtakavarga (their combined total per rasi).
 * <p>
 * Each of the 8 points - the 7 grahas Surya..Shani plus Lagna - contributes benefic points
 * ("Rekha"/"bindu") into specific houses counted from each of those same 8 points' own rasi,
 * following a fixed classical table (one row per contributor, laid out for all 8 possible
 * querents and all 12 possible house offsets). Bhinnashtakavarga for a given point is, for each
 * rasi, how many of the 8 contributors give it a bindu there (0-8); Sarvashtakavarga is the sum
 * of the 7 grahas' (not Lagna's) Bhinnashtakavarga tables, rasi by rasi.
 * <p>
 * This implements only the raw bindu tables - not the further classical refinements
 * (Trikonashodhana/"trine reduction" and Ekadhipatyashodhana/"lordship reduction") that some
 * texts apply on top before deriving Pinda (longevity) figures. Those are a deliberately
 * separate, less commonly needed step; the unreduced Bhinnashtakavarga/Sarvashtakavarga bindus
 * implemented here are what virtually every Jyotish text and piece of software means by
 * "Ashtakavarga" by default.
 * <p>
 * The benefic-point table (`REKHA_MAP`) is classical data with no ephemeris dependency at all -
 * it is not derived here but taken from the reference implementation in
 * <a href="https://github.com/martin-pe/maitreya8">maitreya8</a>
 * (`src/jyotish/Ashtakavarga.cpp`), extracted programmatically rather than hand-transcribed (see
 * `ai-github-projects/swe-jyotisa-lib/extract-rekha-map.py`) and checked against the classical
 * per-graha bindu totals - see {@code AshtakavargaTest}.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class Ashtakavarga {

    /**
     * maitreya8's REKHA_MAP is indexed, for both the contributor (outer) and the querent
     * (middle) dimension, in the order Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn,
     * Ascendant - not {@link ISweObjects}'s own chart-index order (which has Mars, uid 3,
     * before Mercury, uid 4). POINTS lists the 8 chart-index uids in maitreya8's table order,
     * so {@code POINTS[tableIndex]} converts one way and {@link #uidToTableIndex} the other -
     * the giant table itself is never reindexed, only looked up through this pair.
     */
    static final int[] POINTS = {SY, CH, BU, SK, MA, GU, SA, LG};

    static final int[] uidToTableIndex = new int[SA + 1];

    static {
        for (int tableIndex = 0; tableIndex < POINTS.length; tableIndex++) {
            uidToTableIndex[POINTS[tableIndex]] = tableIndex;
        }
    }

    // [contributor][querent][houseOffset 0..11], maitreya8's own index order:
    // Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn, Ascendant - both for the
    // contributor (outer) and the querent (middle) dimension. Extracted verbatim from
    // maitreya8 src/jyotish/Ashtakavarga.cpp's REKHA_MAP by
    // ai-github-projects/swe-jyotisa-lib/extract-rekha-map.py, not hand-transcribed -
    // verified against the classical per-graha bindu totals (48/49/54/52/39/56/39 for
    // Sun..Saturn) in AshtakavargaTest.
    static final int[][][] REKHA_MAP = {
        { // Sun
            {1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Sun
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}, // Moon
            {0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1}, // Mercury
            {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1}, // Venus
            {1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Mars
            {0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0}, // Jupiter
            {1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Saturn
            {0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1}  // Ascendant
        },
        { // Moon
            {0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0}, // Sun
            {1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0}, // Moon
            {1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0}, // Mercury
            {0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0}, // Venus
            {0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0}, // Mars
            {1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0}, // Jupiter
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0}, // Saturn
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}  // Ascendant
        },
        { // Mercury
            {0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1}, // Sun
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0}, // Moon
            {1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1}, // Mercury
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0}, // Venus
            {1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Mars
            {0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1}, // Jupiter
            {1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Saturn
            {1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0}  // Ascendant
        },
        { // Venus
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1}, // Sun
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1}, // Moon
            {0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0}, // Mercury
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0}, // Venus
            {0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1}, // Mars
            {0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0}, // Jupiter
            {0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0}, // Saturn
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0}  // Ascendant
        },
        { // Mars
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, // Sun
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0}, // Moon
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0}, // Mercury
            {0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1}, // Venus
            {1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0}, // Mars
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1}, // Jupiter
            {1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Saturn
            {1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}  // Ascendant
        },
        { // Jupiter
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0}, // Sun
            {0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // Moon
            {1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0}, // Mercury
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0}, // Venus
            {1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0}, // Mars
            {1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0}, // Jupiter
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1}, // Saturn
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0}  // Ascendant
        },
        { // Saturn
            {1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0}, // Sun
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0}, // Moon
            {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1}, // Mercury
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1}, // Venus
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1}, // Mars
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1}, // Jupiter
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0}, // Saturn
            {1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0}  // Ascendant
        },
        { // Ascendant
            {0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1}, // Sun
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1}, // Moon
            {1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0}, // Mercury
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0}, // Venus
            {1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}, // Mars
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0}, // Jupiter
            {1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0}, // Saturn
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}  // Ascendant
        }
    };

    // rekha[contributorUid][rasi0based]
    private final int[][] rekha = new int[SA + 1][12];
    // sarva[rasi0based], sum of the 7 grahas' (not Lagna's) rekha
    private final int[] sarva = new int[12];

    public Ashtakavarga(final IKundaliOptions options, final ISweObjects sweObjects) {
        final int[] signs = sweObjects.signs();
        final int[] rasi0 = new int[SA + 1];
        for (int uid : POINTS) rasi0[uid] = signs[uid] - 1;

        for (int contributorUid : POINTS) {
            final int ci = uidToTableIndex[contributorUid];
            final int[] contributorRekha = rekha[contributorUid];

            for (int querentUid : POINTS) {
                final int[] row = REKHA_MAP[ci][uidToTableIndex[querentUid]];
                final int querentRasi0 = rasi0[querentUid];

                for (int k = 0; k < 12; k++) {
                    if (0 != row[k]) contributorRekha[(querentRasi0 + k) % 12]++;
                }
            }
        }

        for (int rasi0based = 0; rasi0based < 12; rasi0based++) {
            for (int grahaUid = SY; grahaUid <= SA; grahaUid++) sarva[rasi0based] += rekha[grahaUid][rasi0based];
        }
    }

    /**
     * Bhinnashtakavarga: the number of bindus (0-8) the given point receives in the given rasi.
     *
     * @param point one of the 7 classical grahas (Surya..Shani) or Lagna
     * @param rasi  the rasi to count bindus in
     */
    public int bindu(final IGraha point, final IRasi rasi) {
        return rekha[point.uid()][rasi.fid() - 1];
    }

    /**
     * Sarvashtakavarga: the combined bindu total (0-56) of the 7 classical grahas (not Lagna)
     * in the given rasi.
     */
    public int sarva(final IRasi rasi) {
        return sarva[rasi.fid() - 1];
    }

    /** The 8 contributing points, Surya..Shani then Lagna. */
    public IGraha[] points() {
        final IGraha[] result = new IGraha[POINTS.length];
        for (int i = 0; i < POINTS.length; i++) result[i] = EGraha.byUid(POINTS[i]);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(1024);
        builder.append(String.format("%-8s", "BAV:"));
        for (int rasiFid = 1; rasiFid <= 12; rasiFid++) {
            builder.append(String.format("%4s", ERasi.byUid(rasiFid).following()));
        }
        builder.append('\n');

        for (int uid : POINTS) {
            builder.append(String.format("%-8s", EGraha.byUid(uid).code()));
            for (int rasi0based = 0; rasi0based < 12; rasi0based++) {
                builder.append(String.format("%4d", rekha[uid][rasi0based]));
            }
            builder.append('\n');
        }

        builder.append(String.format("%-8s", "Sarva:"));
        for (int rasi0based = 0; rasi0based < 12; rasi0based++) {
            builder.append(String.format("%4d", sarva[rasi0based]));
        }
        builder.append('\n');

        return builder.toString();
    }
}
