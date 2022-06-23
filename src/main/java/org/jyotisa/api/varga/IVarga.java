/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.rasi.IRasi;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.jyotisa.rasi.ERasi.byLongitude;
import static org.swisseph.api.ISweConstants.CHAKRA_LENGTH;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVarga extends IKundaliSequence<IVarga> {

    @Override
    default double length() {
        return CHAKRA_LENGTH;
    }

    default IRasi rasi(double longitudeInD1) {
        return byLongitude(virtualDegree(longitudeInD1));
    }

    /**
     * For finding longitude of an object in any divisional chart (varga chakra)
     * multiply the degrees, minutes, seconds by the number of the Varga
     * (2 for hora, 3 for drekkana and so on) now leave the completed signs
     * and retain the degrees, minutes, seconds as the longitudeInD1
     * of the object for that divisional chart.
     *
     * @return longitude in a sign of the varga
     */
    default double rasiLongitude(double longitudeInD1) {
        return rasiDegree(virtualDegree(longitudeInD1));
    }

    /**
     * For finding a degree (virtual longitude) of an object in any divisional chart (varga chakra)
     * multiply the degrees, minutes, seconds by the number of the Varga (2 for hora and so on).
     *
     * @return degree in a whole varga chakra. It is a virtual longitude needed to calculate {@link IRasi}
     * and you should not use it as a real longitude
     */
    default double virtualDegree(double longitudeInD1) {
        longitudeInD1 *= fid();
        return fix360(longitudeInD1);
    }

    String D01_CD = "D1";
    String D02_CD = "D2";
    String D03_CD = "D3";
    String D04_CD = "D4";
    String D05_CD = "D5";
    String D06_CD = "D6";
    String D07_CD = "D7";
    String D08_CD = "D8";
    String D09_CD = "D9";
    String D10_CD = "D10";
    String D11_CD = "D11";
    String D12_CD = "D12";
    String D16_CD = "D16";
    String D20_CD = "D20";
    String D24_CD = "D24";
    String D27_CD = "D27";
    String D30_CD = "D30";
    String D40_CD = "D40";
    String D45_CD = "D45";
    String D60_CD = "D60";
    String D81_CD = "D81";
    String D108_CD = "D108";
    String D144_CD = "D144";

    static double getDvadasamsaLongitude(final double longitude) {
        return fix360(rasiFid0(longitude) * 30 + rasiDegree(longitude) * 12);
    }
}
