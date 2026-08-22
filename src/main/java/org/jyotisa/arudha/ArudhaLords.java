/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.arudha;

import org.swisseph.api.ISweObjects;

import static org.swisseph.api.ISweConstants.i12;
import static org.swisseph.api.ISweObjects.BU;
import static org.swisseph.api.ISweObjects.CH;
import static org.swisseph.api.ISweObjects.GU;
import static org.swisseph.api.ISweObjects.KE;
import static org.swisseph.api.ISweObjects.MA;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.api.ISweObjects.SA;
import static org.swisseph.api.ISweObjects.SK;
import static org.swisseph.api.ISweObjects.SY;

/**
 * Which graha owns a rasi <b>for the purpose of an arudha</b> - and, for the two signs that have
 * two owners, which of the two is the stronger.
 *
 * <h2>Why this is not simply {@code IRasi.lord()}</h2>
 * Ten signs have one owner and {@code IRasi.lord()} names it. <b>Scorpio and Aquarius have two</b>
 * - Mars with Ketu, and Saturn with Rahu - and Jaimini takes the <i>stronger</i> of the pair. It
 * is not an edge case: whole-sign bhavas cover all twelve signs, so <b>every</b> chart has exactly
 * one Scorpio bhava and one Aquarius bhava, and this decides two of its twelve arudha padas.
 * <p>
 * Measured against the seventeen reference charts: of those 34 co-lorded bhavas the primary lord
 * alone answers 16 correctly, the node alone another 10, and 8 are cases where both give the same
 * sign. Taking {@code IRasi.lord()} unconditionally would therefore be wrong about 10 of 34.
 *
 * <h2>The cascade</h2>
 * Each rung is tried in turn and the first that separates them decides:
 * <ol>
 * <li><b>Occupation.</b> If one of the two sits in the sign they share and the other does not,
 *     the <i>other</i> is stronger. That inversion is the rule as stated, not a slip.</li>
 * <li><b>Company.</b> The one joined by more grahas.</li>
 * <li><b>Jupiter, Mercury and the dispositor</b> - how many of the three conjoin or cast a rasi
 *     aspect on the sign the graha stands in.</li>
 * <li><b>Exaltation.</b> One exalted and the other not.</li>
 * <li><b>The rasi's own nature</b> - dual is stronger than fixed, fixed than movable.</li>
 * <li><b>The one that has travelled less through its rasi</b> - see below.</li>
 * </ol>
 * How often each rung actually decides, over those 34: occupation 5, company 14, the three
 * grahas 8, exaltation 1, nature 2, and 4 reach the last rung.
 *
 * <h2>The last rung runs the other way from the classical statement</h2>
 * The rung is usually stated as "the graha further <i>advanced</i> through its rasi". Jagannatha
 * Hora does the opposite, and the reference charts say so twice over - the only two cases in
 * seventeen charts where the co-lords stand in different signs and every earlier rung ties:
 * <pre>
 * 1800  Aquarius  Saturn 12&deg;16' of Karkata   Rahu  6&deg;33' of Mesha    JHora takes Rahu
 * 2010  Aquarius  Saturn  6&deg;15' of Kanya     Rahu 22&deg;34' of Dhanus   JHora takes Saturn
 * </pre>
 * In both the <b>less</b> advanced wins, and "more advanced" would have got both wrong. Two
 * independent observations pointing the same way is thin but not accidental, and
 * {@code JhoraRefChartsTest} pins them so a third chart can confirm or overturn it.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see ArudhaPadas
 */
public final class ArudhaLords {

    /** the sole owner of each 1-based rasi; Scorpio and Aquarius name only their primary */
    private static final int[] PRIMARY = {0,
            MA, SK, BU, CH, SY, BU, SK, MA, GU, SA, SA, GU};

    /** the co-owner of Scorpio and of Aquarius; 0 for the ten signs that have one owner */
    private static final int[] CO_OWNER = {0,
            0, 0, 0, 0, 0, 0, 0, KE, 0, 0, RA, 0};

    /**
     * The rasi each graha is exalted in. The nodes have two apiece, which is why this is a set of
     * signs rather than one.
     */
    private static final int[][] EXALTED = new int[KE + 1][];

    /** the grahas that count as company - the classical seven and the two nodes */
    static final int[] BODIES = {SY, CH, MA, BU, GU, SK, SA, RA, KE};

    static {
        EXALTED[SY] = new int[]{1};    // Mesha
        EXALTED[CH] = new int[]{2};    // Vrishabha
        EXALTED[MA] = new int[]{10};   // Makara
        EXALTED[BU] = new int[]{6};    // Kanya
        EXALTED[GU] = new int[]{4};    // Karkata
        EXALTED[SK] = new int[]{12};   // Meena
        EXALTED[SA] = new int[]{7};    // Tula
        EXALTED[RA] = new int[]{2, 3};
        EXALTED[KE] = new int[]{8, 9};
    }

    private ArudhaLords() {
    }

    /**
     * The graha that owns a rasi for an arudha, resolving Scorpio and Aquarius by strength.
     *
     * @param rasi  the 1-based rasi
     * @param signs {@link ISweObjects#signs()} - where every graha stands
     * @return the owning graha's object id
     */
    public static int of(final int rasi, final int[] signs, final double[] longitudes) {
        final int primary = PRIMARY[rasi];
        final int co = CO_OWNER[rasi];

        if (0 == co) return primary;
        if (!isCalculated(signs, primary) || !isCalculated(signs, co)) return primary;

        return stronger(rasi, primary, co, signs, longitudes);
    }

    private static boolean isCalculated(final int[] signs, final int graha) {
        return signs[graha] >= 1 && signs[graha] <= i12;
    }

    /**
     * The stronger of a sign's two owners - the cascade above, rung by rung.
     *
     * @param shared the rasi the two share, which the first rung turns on
     */
    static int stronger(final int shared, final int primary, final int co,
                        final int[] signs, final double[] longitudes) {

        final int at = signs[primary], coAt = signs[co];

        // 1. one of them sits in the sign they share - then the other is the stronger
        if (at == shared && coAt != shared) return co;
        if (coAt == shared && at != shared) return primary;

        // 2. the one in more company
        final int company = company(at, signs), coCompany = company(coAt, signs);
        if (company != coCompany) return company > coCompany ? primary : co;

        // 3. Jupiter, Mercury and the dispositor - conjoining or aspecting
        final int seen = seenByTheThree(at, signs), coSeen = seenByTheThree(coAt, signs);
        if (seen != coSeen) return seen > coSeen ? primary : co;

        // 4. exaltation
        final boolean exalted = isExalted(primary, at), coExalted = isExalted(co, coAt);
        if (exalted != coExalted) return exalted ? primary : co;

        // 5. the nature of the rasi each stands in: dual > fixed > movable
        final int nature = nature(at), coNature = nature(coAt);
        if (nature != coNature) return nature > coNature ? primary : co;

        // 6. the one that has travelled less through its rasi - see the class comment
        return degreeIn(primary, signs, longitudes) <= degreeIn(co, signs, longitudes)
                ? primary : co;
    }

    /** how many other grahas share a rasi */
    private static int company(final int rasi, final int[] signs) {
        int found = 0;
        for (int graha : BODIES) if (signs[graha] == rasi) found++;

        return Math.max(0, found - 1);   // the graha itself does not keep itself company
    }

    /**
     * How many of Jupiter, Mercury and the rasi's own dispositor stand in that rasi or cast a
     * <b>rasi</b> aspect on it.
     * <p>
     * The dispositor is taken as the primary lord even for Scorpio and Aquarius: resolving it by
     * strength would call back into the very question being answered.
     */
    private static int seenByTheThree(final int rasi, final int[] signs) {
        int seen = 0;

        for (int graha : new int[]{GU, BU, PRIMARY[rasi]}) {
            final int at = signs[graha];
            if (at == rasi || aspects(at, rasi)) seen++;
        }

        return seen;
    }

    /**
     * Jaimini rasi drishti: a movable rasi sees the fixed ones but the next, a fixed rasi sees the
     * movable ones but the previous, and a dual rasi sees the other duals.
     */
    static boolean aspects(final int from, final int to) {
        if (from < 1 || from > i12 || to < 1 || to > i12) return false;

        final int fromNature = (from - 1) % 3, toNature = (to - 1) % 3;

        if (0 == fromNature) return 1 == toNature && to != from % i12 + 1;
        if (1 == fromNature) return 0 == toNature && to != (from + i12 - 2) % i12 + 1;

        return 2 == toNature && to != from;
    }

    private static boolean isExalted(final int graha, final int rasi) {
        for (int sign : EXALTED[graha]) if (sign == rasi) return true;
        return false;
    }

    /** how far a graha has gone into its own rasi, in degrees */
    private static double degreeIn(final int graha, final int[] signs, final double[] longitudes) {
        return longitudes[graha] - (signs[graha] - 1) * 30.;
    }

    /** movable 1, fixed 2, dual 3 - the order they rank in */
    private static int nature(final int rasi) {
        return (rasi - 1) % 3 + 1;
    }
}
