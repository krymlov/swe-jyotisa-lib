/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.panchanga.IPanchanga;
import org.swisseph.utils.IDegreeUtils;

import static org.swisseph.api.ISweConstants.D100_KARANA_LENGTH;
import static org.swisseph.api.ISweConstants.KARANA_LENGTH;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IKarana extends IKundaliSequence<IKarana> {

    @Override
    default double length() {
        return KARANA_LENGTH;
    }

    static double progress(final IPanchanga panchanga) {
        return IDegreeUtils.round(modulo(KARANA_LENGTH,
            panchanga.chandraLongitude()-panchanga.suryaLongitude())
                * D100_KARANA_LENGTH,2);
    }

    // Karana
    String KR01_CD = "KR1";  // 1 Bava
    String KR02_CD = "KR2";  // 2 Balava
    String KR03_CD = "KR3";  // 3 Kaulava
    String KR04_CD = "KR4";  // 4 Taitula
    String KR05_CD = "KR5";  // 5 Garija
    String KR06_CD = "KR6";  // 6 Vanija
    String KR07_CD = "KR7";  // 7 Vishti
    String KR08_CD = "KR8";  // 8 Sakuna
    String KR09_CD = "KR9";  // 9 Chatushpada
    String KR10_CD = "KR10"; // 10 Naga
    String KR11_CD = "KR11"; // 11 Kimstughna
}
