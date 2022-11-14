/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-08
*/

package org.jyotisa.graha;


import org.jyotisa.api.graha.*;
import org.jyotisa.graha.budha.GrahaBudha;
import org.jyotisa.graha.chandra.GrahaChandra;
import org.jyotisa.graha.chaya.GrahaKetu;
import org.jyotisa.graha.chaya.GrahaRahu;
import org.jyotisa.graha.guru.GrahaGuru;
import org.jyotisa.graha.lagna.GrahaLagna;
import org.jyotisa.graha.mangala.GrahaMangala;
import org.jyotisa.graha.shani.GrahaShani;
import org.jyotisa.graha.shukra.GrahaShukra;
import org.jyotisa.graha.surya.GrahaSurya;
import org.jyotisa.graha.sweta.GrahaSweta;
import org.jyotisa.graha.syama.GrahaSyama;
import org.jyotisa.graha.teevra.GrahaTeevra;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.swisseph.api.ISweConstants.i1;

/**
 * @author  Yura Krymlov
 * @version 1.1, 2019-08
 */
public enum EGraha implements IGrahaEnum {
    LAGNA {@Override public IGrahaLagna graha() { return GrahaLagna.LG; }},
    SURYA {@Override public IGrahaSurya graha() { return GrahaSurya.SY; }},
    CHANDRA {@Override public IGrahaChandra graha() { return GrahaChandra.CH; }},
    MANGALA {@Override public IGrahaMangala graha() { return GrahaMangala.MA; }},
    BUDHA {@Override public IGrahaBudha graha() { return GrahaBudha.BU; }},
    GURU {@Override public IGrahaGuru graha() { return GrahaGuru.GU; }},
    SHUKRA {@Override public IGrahaShukra graha() { return GrahaShukra.SK; }},
    SHANI {@Override public IGrahaShani graha() { return GrahaShani.SA; }},
    RAHU {@Override public IGrahaRahu graha() { return GrahaRahu.RA; }},
    KETU {@Override public IGrahaKetu graha() { return GrahaKetu.KE; }},
    SWETA {@Override public IGrahaSweta graha() { return GrahaSweta.SW; }},
    SYAMA {@Override public IGrahaSyama graha() { return GrahaSyama.SM; }},
    TEEVRA {@Override public IGrahaTeevra graha() { return GrahaTeevra.TE; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IGrahaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IGrahaEnum> iterator() {
        return new SweEnumIterator<>(values(), SURYA.ordinal());
    }

    public static ISweEnumIterator<IGrahaEnum> iteratorNodes() {
        return new SweEnumIterator<>(values(), RAHU.ordinal());
    }

    public static ISweEnumIterator<IGrahaEnum> iteratorNoNodes() {
        return new SweEnumIterator<>(values(), i1, SHANI.ordinal());
    }

    public static ISweEnumIterator<IGrahaEnum> iteratorFrom(final IGrahaEnum graha) {
        return new SweEnumIterator<>(values(), graha.ordinal());
    }

    public static ISweEnumIterator<IGrahaEnum> iteratorTo(final IGrahaEnum graha) {
        return new SweEnumIterator<>(values(), SURYA.ordinal(), graha.ordinal());
    }

    public static ISweEnumIterator<IGrahaEnum> iterator(final IGrahaEnum grahaFrom, final IGrahaEnum grahaTo) {
        return new SweEnumIterator<>(values(), grahaFrom.ordinal(), grahaTo.ordinal());
    }

    public static IGrahaEnum byGraha(final IGraha graha) {
        return ISweEnum.byCode(graha.code(), values());
    }

    public static IGraha byFid(int fid) {
        return ISweEnum.byFid(fid, values()).graha();
    }

    public static IGraha byUid(int uid) {
        return ISweEnum.byUid(uid, values()).graha();
    }

    public static IGraha byIndex(int index) {
        return ISweEnum.byIndex(index, values()).graha();
    }

    public static IGraha byCode(String code) {
        return ISweEnum.byCode(code, values()).graha();
    }

    public static IGraha byName(final String name) {
        final EGraha[] values = values();
        for (int i = 1; i < values.length; i++) {
            IGraha value = values[i].graha().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).graha();
    }
}
