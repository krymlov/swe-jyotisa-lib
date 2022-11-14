/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-08
*/

package org.jyotisa.rasi;


import org.jyotisa.api.rasi.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.jyotisa.api.rasi.IRasi.rasiFid;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-08
 */
public enum ERasi implements IRasiEnum {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public IRasi rasi() { return null; }
    }, // 0  Reserved
    MESHA {@Override public IRasiMesha rasi() { return RasiMesha.R1; }},
    VRISHABHA {@Override public IRasiVrishabha rasi() { return RasiVrishabha.R2; }},
    MITHUNA {@Override public IRasiMithuna rasi() { return RasiMithuna.R3; }},
    KARKATA {@Override public IRasiKarkata rasi() { return RasiKarkata.R4; }},
    SIMHA {@Override public IRasiSimha rasi() { return RasiSimha.R5; }},
    KANYA {@Override public IRasiKanya rasi() { return RasiKanya.R6; }},
    TULA {@Override public IRasiTula rasi() { return RasiTula.R7; }},
    VRISCHIKA {@Override public IRasiVrischika rasi() { return RasiVrischika.R8; }},
    DHANUS {@Override public IRasiDhanus rasi() { return RasiDhanus.R9; }},
    MAKARA {@Override public IRasiMakara rasi() { return RasiMakara.R10; }},
    KUMBHA {@Override public IRasiKumbha rasi() { return RasiKumbha.R11; }},
    MEENA {@Override public IRasiMeena rasi() { return RasiMeena.R12; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IRasiEnum first() {
        return MESHA;
    }

    @Override
    public IRasiEnum last() {
        return MEENA;
    }

    @Override
    public IRasiEnum[] all() {
        return values();
    }

    public static IRasiEnum byRasi(final IRasi rasi) {
        return ISweEnum.byCode(rasi.code(), values());
    }

    public static ISweEnumIterator<IRasiEnum> iterator() {
        return new SweEnumIterator<>(values(), MESHA.ordinal());
    }

    public static ISweEnumIterator<IRasiEnum> iteratorFrom(final IRasiEnum rasi) {
        return new SweEnumIterator<>(values(), rasi.ordinal());
    }

    public static ISweEnumIterator<IRasiEnum> iteratorTo(final IRasiEnum rasi) {
        return new SweEnumIterator<>(values(), MESHA.ordinal(), rasi.ordinal());
    }

    public static ISweEnumIterator<IRasiEnum> iterator(final IRasiEnum rasiFrom, final IRasiEnum rasiTo) {
        return new SweEnumIterator<>(values(), rasiFrom.ordinal(), rasiTo.ordinal());
    }

    public static IRasi byLongitude(final double longitude) {
        return values()[rasiFid(longitude)].rasi();
    }

    public static IRasi byName(final String name) {
        final ERasi[] values = values();
        for (int i = 1; i < values.length; i++) {
            IRasi value = values[i].rasi().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).rasi();
    }

    public static IRasi byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).rasi();
    }

    public static IRasi byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).rasi();
    }

}
