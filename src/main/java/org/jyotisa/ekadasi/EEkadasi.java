/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum EEkadasi implements IEkadasiEnum {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public IEkadasi ekadasi() { return null; }
    }, // 0  Reserved
    UTPANNA {@Override public IUtpannaEkadasi ekadasi() { return UtpannaEkadasi.EK1; }},
    MOKSADA {@Override public IMoksadaEkadasi ekadasi() { return MoksadaEkadasi.EK2; }},
    SAPHALA {@Override public ISaphalaEkadasi ekadasi() { return SaphalaEkadasi.EK3; }},
    PUTRADA {@Override public IPutradaEkadasi ekadasi() { return PutradaEkadasi.EK4; }},
    SATTILA {@Override public ISattilaEkadasi ekadasi() { return SattilaEkadasi.EK5; }},
    JAYA {@Override public IJayaEkadasi ekadasi() { return JayaEkadasi.EK6; }},
    VIJAYA {@Override public IVijayaEkadasi ekadasi() { return VijayaEkadasi.EK7; }},
    AMALAKI {@Override public IAmalakiEkadasi ekadasi() { return AmalakiEkadasi.EK8; }},
    PAAPMOCHANI {@Override public IPaapmochaniEkadasi ekadasi() { return PaapmochaniEkadasi.EK9; }},
    KAMADA {@Override public IKamadaEkadasi ekadasi() { return KamadaEkadasi.EK10; }},
    VARUTHINI {@Override public IVaruthiniEkadasi ekadasi() { return VaruthiniEkadasi.EK11; }},
    MOHINI {@Override public IMohiniEkadasi ekadasi() { return MohiniEkadasi.EK12; }},
    APARA {@Override public IAparaEkadasi ekadasi() { return AparaEkadasi.EK13; }},
    NIRJALA {@Override public INirjalaEkadasi ekadasi() { return NirjalaEkadasi.EK14; }},
    YOGINI {@Override public IYoginiEkadasi ekadasi() { return YoginiEkadasi.EK15; }},
    SAYANA {@Override public ISayanaEkadasi ekadasi() { return SayanaEkadasi.EK16; }},
    KAMIKA {@Override public IKamikaEkadasi ekadasi() { return KamikaEkadasi.EK17; }},
    PAVITROPANA {@Override public IPavitropanaEkadasi ekadasi() { return PavitropanaEkadasi.EK18; }},
    ANNADA {@Override public IAnnadaEkadasi ekadasi() { return AnnadaEkadasi.EK19; }},
    PARSVA {@Override public IParsvaEkadasi ekadasi() { return ParsvaEkadasi.EK20; }},
    INDIRA {@Override public IIndiraEkadasi ekadasi() { return IndiraEkadasi.EK21; }},
    PASANKUSA {@Override public IPasankusaEkadasi ekadasi() { return PasankusaEkadasi.EK22; }},
    RAMA {@Override public IRamaEkadasi ekadasi() { return RamaEkadasi.EK23; }},
    UTTHANA {@Override public IUtthanaEkadasi ekadasi() { return UtthanaEkadasi.EK24; }},
    PADMINI {@Override public IPadminiEkadasi ekadasi() { return PadminiEkadasi.EK25; }},
    PARAMA {@Override public IParamaEkadasi ekadasi() { return ParamaEkadasi.EK26; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IEkadasiEnum first() {
        return UTPANNA;
    }

    @Override
    public IEkadasiEnum last() {
        return PARAMA;
    }

    @Override
    public IEkadasiEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IEkadasiEnum> iterator() {
        return new SweEnumIterator<>(values(), UTPANNA.uid());
    }

    public static ISweEnumIterator<IEkadasiEnum> iteratorFrom(final IEkadasiEnum ekadasi) {
        return new SweEnumIterator<>(values(), ekadasi.uid());
    }

    public static ISweEnumIterator<IEkadasiEnum> iteratorTo(final IEkadasiEnum ekadasi) {
        return new SweEnumIterator<>(values(), UTPANNA.uid(), ekadasi.uid());
    }

    public static IEkadasi byName(final String name) {
        final EEkadasi[] values = values();
        for (int i = 1; i < values.length; i++) {
            IEkadasi value = values[i].ekadasi().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).ekadasi();
    }

    public static IEkadasi byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).ekadasi();
    }

    public static IEkadasi byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).ekadasi();
    }

}
