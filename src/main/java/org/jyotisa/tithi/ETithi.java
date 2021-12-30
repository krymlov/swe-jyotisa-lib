/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-10
*/

package org.jyotisa.tithi;

import org.jyotisa.api.tithi.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.swisseph.api.ISweConstants.d12;
import static org.swisseph.api.ISweConstants.i1;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author  Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum ETithi implements ITithiEnum {
    NIL {
        @Override public int fid() { return NIL_FID; }
        @Override public String code() { return NIL_CD; }
        @Override public ITithi tithi() { return null; }
    }, // 0  Reserved

    SHUKLA_PRATIPADA {@Override public ITithiPratipada tithi() { return TithiPratipada.S1; }},
    SHUKLA_DWITIYA {@Override public ITithiDwitiya tithi() { return TithiDwitiya.S2; }},
    SHUKLA_TRITIYA {@Override public ITithiTritiya tithi() { return TithiTritiya.S3; }},
    SHUKLA_CHATURTHI {@Override public ITithiChaturthi tithi() { return TithiChaturthi.S4; }},
    SHUKLA_PANCHAMI {@Override public ITithiPanchami tithi() { return TithiPanchami.S5; }},
    SHUKLA_SHASHTHI {@Override public ITithiShashthi tithi() { return TithiShashthi.S6; }},
    SHUKLA_SAPTAMI {@Override public ITithiSaptami tithi() { return TithiSaptami.S7; }},
    SHUKLA_ASHTAMI {@Override public ITithiAshtami tithi() { return TithiAshtami.S8; }},
    SHUKLA_NAVAMI {@Override public ITithiNavami tithi() { return TithiNavami.S9; }},
    SHUKLA_DASHAMI {@Override public ITithiDashami tithi() { return TithiDashami.S10; }},
    SHUKLA_EKADASI {@Override public ITithiEkadasi tithi() { return TithiEkadasi.S11; }},
    SHUKLA_DWADASI {@Override public ITithiDwadasi tithi() { return TithiDwadasi.S12; }},
    SHUKLA_TRAYODASI {@Override public ITithiTrayodasi tithi() { return TithiTrayodasi.S13; }},
    SHUKLA_CHATURDASI {@Override public ITithiChaturdasi tithi() { return TithiChaturdasi.S14; }},
    SHUKLA_POORNIMA {@Override public ITithiPoornima tithi() { return TithiPoornima.S15; }},

    KRISHNA_PRATIPADA {@Override public ITithiPratipada tithi() { return TithiPratipada.K1; }},
    KRISHNA_DWITIYA {@Override public ITithiDwitiya tithi() { return TithiDwitiya.K2; }},
    KRISHNA_TRITIYA {@Override public ITithiTritiya tithi() { return TithiTritiya.K3; }},
    KRISHNA_CHATURTHI {@Override public ITithiChaturthi tithi() { return TithiChaturthi.K4; }},
    KRISHNA_PANCHAMI {@Override public ITithiPanchami tithi() { return TithiPanchami.K5; }},
    KRISHNA_SHASHTHI {@Override public ITithiShashthi tithi() { return TithiShashthi.K6; }},
    KRISHNA_SAPTAMI {@Override public ITithiSaptami tithi() { return TithiSaptami.K7; }},
    KRISHNA_ASHTAMI {@Override public ITithiAshtami tithi() { return TithiAshtami.K8; }},
    KRISHNA_NAVAMI {@Override public ITithiNavami tithi() { return TithiNavami.K9; }},
    KRISHNA_DASHAMI {@Override public ITithiDashami tithi() { return TithiDashami.K10; }},
    KRISHNA_EKADASI {@Override public ITithiEkadasi tithi() { return TithiEkadasi.K11; }},
    KRISHNA_DWADASI {@Override public ITithiDwadasi tithi() { return TithiDwadasi.K12; }},
    KRISHNA_TRAYODASI {@Override public ITithiTrayodasi tithi() { return TithiTrayodasi.K13; }},
    KRISHNA_CHATURDASI {@Override public ITithiChaturdasi tithi() { return TithiChaturdasi.K14; }},
    KRISHNA_AMAVASYA {@Override public ITithiPoornima tithi() { return TithiPoornima.K15; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public ITithiEnum first() {
        return SHUKLA_PRATIPADA;
    }

    @Override
    public ITithiEnum last() {
        return KRISHNA_AMAVASYA;
    }

    @Override
    public ITithiEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<ITithiEnum> iterator() {
        return new SweEnumIterator<>(values(), SHUKLA_PRATIPADA.uid());
    }

    public static ISweEnumIterator<ITithiEnum> iteratorFrom(final ITithiEnum tithi) {
        return new SweEnumIterator<>(values(), tithi.uid());
    }

    public static ISweEnumIterator<ITithiEnum> iteratorTo(final ITithiEnum tithi) {
        return new SweEnumIterator<>(values(), SHUKLA_PRATIPADA.uid(), tithi.uid());
    }

    /**
     * Tithi = (Chandra Longitude - Surya Longitude) / 12 deg.
     */
    public static ITithi byLongitude(final double surya, final double chandra) {
        return byOffset(chandra - surya);
    }

    public static ITithiEnum byTithi(final ITithi tithi) {
        return ISweEnum.byCode(tithi.code(), values());
    }

    /**
     * @param offset is (Chandra longitude - Surya longitude)
     */
    public static ITithi byOffset(final double offset) {
        return byUid(i1 + (int)(fix360(offset) / d12));
    }

    public static ITithi byName(final String name) {
        final ETithi[] values = values();
        for (int i = 1; i <= 15; i++) {
            ITithi value = values[i].tithi().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).tithi();
    }

    public static ITithi byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).tithi();
    }

    public static ITithi byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).tithi();
    }
}
