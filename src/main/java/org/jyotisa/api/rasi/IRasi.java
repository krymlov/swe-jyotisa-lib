/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.rasi;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.tattva.ITattva;
import org.swisseph.api.ISweGender;
import org.swisseph.utils.IDegreeUtils;

import static org.swisseph.api.ISweConstants.D100_RASI_LENGTH;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.utils.IModuloUtils.fix360;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IRasi extends IKundaliSequence<IRasi> {
    // Rasis
    String R01_CD = "R1";
    String R02_CD = "R2";
    String R03_CD = "R3";
    String R04_CD = "R4";
    String R05_CD = "R5";
    String R06_CD = "R6";
    String R07_CD = "R7";
    String R08_CD = "R8";
    String R09_CD = "R9";
    String R10_CD = "R10";
    String R11_CD = "R11";
    String R12_CD = "R12";

    ISweGender gender();

    ITattva tattva();

    IGraha lord();

    /**
     * There is this concept of Badhaka in Vedic Astrology.
     * It is said that it was originally expounded by Varahamihira,
     * and the concept is not found in Brihat Parasara Hora Shastra.
     *
     * The actual meaning of the word in Sanskrit is "that which sublates or sets aside",
     * while the meaning in Pali is "that which prevents, harasses and obstructs" (Source).
     *
     * Each sign of the Zodiac has a Badhaka sign (the sign that opposes it, harasses and obstructs),
     * as well as a Badhaka planet (viz. The ruler of the Badhaka sign).
     *
     * So knowing which sign is influencing you at the moment, will allow you to determine
     * the Badhaka influences on your life, viz. The influences that are obstructing you
     * in achieving the qualities expounded by that sign. Note that Badhaka does not necessarily
     * mean 'denial', but only 'obstruction'. Also, it obstructs the sign, and not the entire horoscope.
     *
     * @return
     */
    IRasi badhaka();

    /**
     * The Movable signs are Aries, Cancer, Libra and Capricorn
     */
    default boolean movable() {
        return false;
    }

    /**
     * the Fixed signs are Taurus, Leo, Scorpio and Aquarius;
     */
    default boolean fixed() {
        return false;
    }

    /**
     * the Dual signs are Gemini, Virgo, Sagittarius and Pisces
     */
    default boolean dual() {
        return false;
    }

    @Override
    default double length() {
        return RASI_LENGTH;
    }

    static double progress(final double longitude) {
        return IDegreeUtils.round(modulo(RASI_LENGTH, longitude)
                * D100_RASI_LENGTH, 2);
    }

    static double rasiDegree(final double longitude) {
        return modulo(RASI_LENGTH, longitude);
    }

    /**
     * @return ARIES = 1, TAURUS, GEMINI, CANCER, LEO, VIRGO,
     * LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES = 12
     */
    static int rasiFid(final double longitude) {
        int fid = rasiFid0(longitude);
        return ++fid;
    }

    /**
     * @return ARIES = 0, TAURUS, GEMINI, CANCER, LEO, VIRGO,
     * LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES = 11
     */
    static int rasiFid0(final double longitude) {
        return (int) (fix360(longitude) / RASI_LENGTH);
    }

    static boolean inOddRasi(double longitude) {
        longitude += RASI_LENGTH;
        return (rasiFid0(longitude) % 2) == 1;
    }

    static boolean inMovableRasi(final double longitude) {
        return rasiFid0(longitude) % 3 == 0;
    }

    static boolean inFixedRasi(final double longitude) {
        return rasiFid0(longitude) % 3 == 1;
    }

    static boolean inDualRasi(final double longitude) {
        return rasiFid0(longitude) % 3 == 2;
    }

}
