/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.panchanga.IPanchanga;
import org.swisseph.api.ISweSegment;
import org.swisseph.app.SweSegment;
import org.swisseph.utils.IDegreeUtils;

import static org.jyotisa.tithi.Paksa.GAURA;
import static org.jyotisa.tithi.Paksa.KRSNA;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.app.SweSegment.ZERO_SEGMENT;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface ITithi extends IKundaliSequence<ITithi> {
    IGraha lord();

    @Override
    default double length() {
        return TITHI_LENGTH;
    }

    default IPaksa paksa() {
        return (ordinal() % 2 == 0) ? KRSNA : GAURA;
    }

    @Override
    default ISweSegment segment() {
        if (0 == fid() && NIL_CD.equals(code())) {
            return ZERO_SEGMENT;
        }

        final int idx = uid();
        final double length = length();
        return new SweSegment((idx - i1) * length, idx * length);
    }

    static double progress(final IPanchanga panchanga) {
        return IDegreeUtils.round(modulo(TITHI_LENGTH,
            panchanga.chandraLongitude() - panchanga.suryaLongitude())
                * D100_TITHI_LENGTH, 2);
    }

    // Tithis

    // Shukla paksha (bright fortnight)
    String S01_CD = "S1";  // 1.  Pratipada
    String S02_CD = "S2";  // 2.  Dwitiya
    String S03_CD = "S3";  // 3.  Tritiya
    String S04_CD = "S4";  // 4.  Chaturthi
    String S05_CD = "S5";  // 5.  Panchami
    String S06_CD = "S6";  // 6.  Shashthi
    String S07_CD = "S7";  // 7.  Saptami
    String S08_CD = "S8";  // 8.  Ashtami
    String S09_CD = "S9";  // 9.  Navami
    String S10_CD = "S10"; // 10. Dashami
    String S11_CD = "S11"; // 11. Ekadasi
    String S12_CD = "S12"; // 12. Dwadashi
    String S13_CD = "S13"; // 13. Trayodashi
    String S14_CD = "S14"; // 14. Chaturdashi
    String S15_CD = "S15"; // 15. Purnima

    // Krishna paksha (dark fortnight)
    String K01_CD = "K1";  // 1. Pratipada
    String K02_CD = "K2";  // 2. Dwitiya
    String K03_CD = "K3";  // 3. Tritiya
    String K04_CD = "K4";  // 4. Chaturthi
    String K05_CD = "K5";  // 5. Panchami
    String K06_CD = "K6";  // 6. Shashthi
    String K07_CD = "K7";  // 7. Saptami
    String K08_CD = "K8";  // 8. Ashtami
    String K09_CD = "K9";  // 9. Navami
    String K10_CD = "K10"; // 10. Dashami
    String K11_CD = "K11"; // 11. Ekadasi
    String K12_CD = "K12"; // 12. Dwadashi
    String K13_CD = "K13"; // 13. Trayodashi
    String K14_CD = "K14"; // 14. Chaturdashi
    String K15_CD = "K15"; // 15. Amavasya
}
