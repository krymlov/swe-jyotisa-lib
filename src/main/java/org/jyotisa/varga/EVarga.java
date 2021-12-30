/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-08
 */

package org.jyotisa.varga;

import org.jyotisa.api.varga.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-08
 */
public enum EVarga implements IVargaEnum {
    NIL {
        @Override public int fid() { return NIL_FID; }
        @Override public String code() { return NIL_CD; }
        @Override public IVarga varga() {return null;}
    }, // 0  Reserved
    RASI {@Override public IVargaD1 varga() {return VargaD1.D1;}},
    HORA {@Override public IVargaD2 varga() {return VargaD2.D2;}},
    DREKKANA {@Override public IVargaD3 varga() {return VargaD3.D3;}},
    CHATURTHAMSA {@Override public IVargaD4 varga() {return VargaD4.D4;}},
    PANCHAMSA {@Override public IVargaD5 varga() {return VargaD5.D5;}},
    SHASHTHAMSA {@Override public IVargaD6 varga() {return VargaD6.D6;}},
    SAPTAMSA {@Override public IVargaD7 varga() {return VargaD7.D7;}},
    ASHTAMSA {@Override public IVargaD8 varga() {return VargaD8.D8;}},
    NAVAMSA {@Override public IVargaD9 varga() {return VargaD9.D9;}},
    DASAMSA {@Override public IVargaD10 varga() {return VargaD10.D10;}},
    RUDRAMSA {@Override public IVargaD11 varga() {return VargaD11.D11;}},
    DWADASAMSA {@Override public IVargaD12 varga() {return VargaD12.D12;}},
    SHODASAMSA {@Override public IVargaD16 varga() {return VargaD16.D16;}},
    VIMSAMSA {@Override public IVargaD20 varga() {return VargaD20.D20;}},
    CHATURVIMSAMSA {@Override public IVargaD24 varga() {return VargaD24.D24;}},
    NAKSHATRAMSA {@Override public IVargaD27 varga() {return VargaD27.D27;}},
    TRIMSAMSA {@Override public IVargaD30 varga() {return VargaD30.D30;}},
    KHAVEDAMSA {@Override public IVargaD40 varga() {return VargaD40.D40;}},
    AKSHAVEDAMSA {@Override public IVargaD45 varga() {return VargaD45.D45;}},
    SHASHTYAMSA {@Override public IVargaD60 varga() {return VargaD60.D60;}},
    NAVANAVAMSA {@Override public IVargaD81 varga() {return VargaD81.D81;}},
    ASHTOTTARAMSA {@Override public IVargaD108 varga() {return VargaD108.D108;}},
    DVADASDVADASAMSA {@Override public IVargaD144 varga() {return VargaD144.D144;}};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IVargaEnum first() {
        return RASI;
    }

    @Override
    public IVargaEnum last() {
        return DVADASDVADASAMSA;
    }

    @Override
    public IVargaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IVargaEnum> iterator() {
        return new SweEnumIterator<>(values(), RASI.ordinal());
    }

    public static ISweEnumIterator<IVargaEnum> iteratorFrom(final IVargaEnum varga) {
        return new SweEnumIterator<>(values(), varga.ordinal());
    }

    public static ISweEnumIterator<IVargaEnum> iteratorTo(final IVargaEnum varga) {
        return new SweEnumIterator<>(values(), RASI.ordinal(), varga.ordinal());
    }

    public static IVargaEnum byVarga(final IVarga varga) {
        return ISweEnum.byCode(varga.code(), values());
    }

    public static IVarga byName(final String name) {
        final EVarga[] values = values();
        for (int i = 1; i < values.length; i++) {
            IVarga value = values[i].varga().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).varga();
    }

    public static IVarga byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).varga();
    }

    public static IVarga byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).varga();
    }
}
