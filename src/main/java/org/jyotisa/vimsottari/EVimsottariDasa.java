/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.vimsottari;

import org.jyotisa.api.vimsottari.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * Vimsottari Dasa is the most important Dasa of the Vedic astrology.<br>
 * Vimsottari is a Nakshatra Dasa that is based upon the position of the Moon in her Lunar mansion.<br>
 * Each Nakshatra has a planet (or Node) as its lord. There are in total 9 lords.
 * <br><br>
 * Each of the lords has a fixed period of influence. The total period is of 120 years.<br>
 * Calculation of Dasa balance is described in the chapter above.
 *
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public enum EVimsottariDasa implements IVimsottariDasaEnum {
    NIL {
        @Override public int fid() { return NIL_FID; }
        @Override public String code() { return NIL_CD; }
        @Override public IVimsottariDasa dasa() { return null; }
    }, // 0  Reserved
    SURYA_DASA {@Override public IVimsottariDasaSurya dasa() {return VimsottariDasaSurya.VD1;}},
    CHANDRA_DASA {@Override public IVimsottariDasaChandra dasa() {return VimsottariDasaChandra.VD2;}},
    MANGALA_DASA {@Override public IVimsottariDasaMangala dasa() {return VimsottariDasaMangala.VD3;}},
    RAHU_DASA {@Override public IVimsottariDasaRahu dasa() {return VimsottariDasaRahu.VD4;}},
    GURU_DASA {@Override public IVimsottariDasaGuru dasa() {return VimsottariDasaGuru.VD5;}},
    SHANI_DASA {@Override public IVimsottariDasaShani dasa() {return VimsottariDasaShani.VD6;}},
    BUDHA_DASA {@Override public IVimsottariDasaBudha dasa() {return VimsottariDasaBudha.VD7;}},
    KETU_DASA {@Override public IVimsottariDasaKetu dasa() {return VimsottariDasaKetu.VD8;}},
    SHUKRA_DASA {@Override public IVimsottariDasaShukra dasa() {return VimsottariDasaShukra.VD9;}};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IVimsottariDasaEnum first() {
        return SURYA_DASA;
    }

    @Override
    public IVimsottariDasaEnum last() {
        return SHUKRA_DASA;
    }

    @Override
    public IVimsottariDasaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IVimsottariDasaEnum> iterator() {
        return new SweEnumIterator<>(values(), SURYA_DASA.ordinal());
    }

    public static ISweEnumIterator<IVimsottariDasaEnum> iteratorFrom(final IVimsottariDasaEnum vimsottari) {
        return new SweEnumIterator<>(values(), vimsottari.ordinal());
    }

    public static ISweEnumIterator<IVimsottariDasaEnum> iteratorTo(final IVimsottariDasaEnum vimsottari) {
        return new SweEnumIterator<>(values(), SURYA_DASA.ordinal(), vimsottari.ordinal());
    }

    public static IVimsottariDasaEnum byDasa(final IVimsottariDasa vimsottariDasa) {
        return ISweEnum.byCode(vimsottariDasa.code(), values());
    }

    public static IVimsottariDasa byName(final String name) {
        final EVimsottariDasa[] values = values();
        for (int i = 1; i < values.length; i++) {
            IVimsottariDasa value = values[i].dasa().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).dasa();
    }

    public static IVimsottariDasa byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).dasa();
    }

    public static IVimsottariDasa byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).dasa();
    }
}
