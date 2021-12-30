/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.panchanga.IPanchanga;

import static org.swisseph.api.ISweConstants.D100_NAKSHATRA_LENGTH;
import static org.swisseph.api.ISweConstants.NAKSHATRA_LENGTH;
import static org.swisseph.utils.IDegreeUtils.round;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatra extends IKundaliSequence<INaksatra> {
    IGraha lord();

    @Override
    default double length() {
        return NAKSHATRA_LENGTH;
    }

    static double progress(final double longitude) {
        return round(modulo(NAKSHATRA_LENGTH, longitude)
                * D100_NAKSHATRA_LENGTH, 2);
    }

    static double progress(final IPanchanga panchanga) {
        return round(modulo(NAKSHATRA_LENGTH,
                panchanga.chandraLongitude()) * D100_NAKSHATRA_LENGTH, 2);
    }

    String N01_CD = "N1";   // 1 Ashwini
    String N02_CD = "N2";   // 2 Bharani
    String N03_CD = "N3";   // 3 Krittika
    String N04_CD = "N4";   // 4 Rohini
    String N05_CD = "N5";   // 5 Mrigashira
    String N06_CD = "N6";   // 6 Ardra
    String N07_CD = "N7";   // 7 Punarvasu
    String N08_CD = "N8";   // 8 Pushya
    String N09_CD = "N9";   // 9 Ashlesha
    String N10_CD = "N10";  // 10 Magha
    String N11_CD = "N11";  // 11 PurvaPhalguni
    String N12_CD = "N12";  // 12 UttaraPhalguni
    String N13_CD = "N13";  // 13 Hasta
    String N14_CD = "N14";  // 14 Chitra
    String N15_CD = "N15";  // 15 Swati
    String N16_CD = "N16";  // 16 Vishakha
    String N17_CD = "N17";  // 17 Anuradha
    String N18_CD = "N18";  // 18 Jyeshtha
    String N19_CD = "N19";  // 19 Mula
    String N20_CD = "N20";  // 20 PurvaAshadha
    String N21_CD = "N21";  // 21 UttaraAshadha
    String N22_CD = "N22";  // 22 Shravana
    String N23_CD = "N23";  // 23 Dhanishta
    String N24_CD = "N24";  // 24 Shatabhisha
    String N25_CD = "N25";  // 25 Purva Bhadrapada
    String N26_CD = "N26";  // 26 Uttara Bhadrapada
    String N27_CD = "N27";  // 27 Revati
}
