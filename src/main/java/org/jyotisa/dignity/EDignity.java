/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-09
 */

package org.jyotisa.dignity;


import org.jyotisa.api.dignity.NilDignity;
import org.jyotisa.api.dignity.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * Natural dignities
 *
 * @author Yura Krymlov
 * @version 1.1, 2019-09
 */
public enum EDignity implements IDignityEnum {
    NIL {@Override public NilDignity dignity() { return NilDignity.NIL; }}, // 0  Reserved - fid()/code() come from the Null Object
    NEECHA {@Override public IDignityNeecha dignity() {return DignityNeecha.DG1; }},
    DEFICIENT {@Override public IDignityDeficient dignity() {return DignityDeficient.DG2; }},
    ADHISATRU {@Override public IDignityAdhisatru dignity() {return DignityAdhisatru.DG3; }},
    SATRU {@Override public IDignitySatru dignity() {return DignitySatru.DG4; }},
    SAMA {@Override public IDignitySama dignity() {return DignitySama.DG5; }},
    MITRA {@Override public IDignityMitra dignity() {return DignityMitra.DG6; }},
    ADHIMITRA {@Override public IDignityAdhimitra dignity() {return DignityAdhimitra.DG7; }},
    SWAKSHETRA {@Override public IDignitySwakshetra dignity() {return DignitySwakshetra.DG8; }},
    MULATRIKONA {@Override public IDignityMulatrikona dignity() {return DignityMulatrikona.DG9; }},
    EXCELLENT {@Override public IDignityExcellent dignity() {return DignityExcellent.DG10; }},
    UCCHA {@Override public IDignityUccha dignity() {return DignityUccha.DG11; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IDignityEnum first() {
        return NEECHA;
    }

    @Override
    public IDignityEnum last() {
        return UCCHA;
    }

    @Override
    public IDignityEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IDignityEnum> iterator() {
        return new SweEnumIterator<>(values(), NEECHA.ordinal());
    }

    public static ISweEnumIterator<IDignityEnum> iteratorFrom(final IDignityEnum dignity) {
        return new SweEnumIterator<>(values(), dignity.ordinal());
    }

    public static ISweEnumIterator<IDignityEnum> iteratorTo(final IDignityEnum dignity) {
        return new SweEnumIterator<>(values(), NEECHA.ordinal(), dignity.ordinal());
    }

    public static ISweEnumIterator<IDignityEnum> iterator(final IDignityEnum dignityFrom, final IDignityEnum dignityTo) {
        return new SweEnumIterator<>(values(), dignityFrom.ordinal(), dignityTo.ordinal());
    }

    public static IDignityEnum byDignity(final IDignity dignity) {
        return ISweEnum.byCode(dignity.code(), values());
    }

    public static IDignity byName(final String name) {
        final EDignity[] values = values();
        for (int i = 1; i < values.length; i++) {
            IDignity value = values[i].dignity().findByName(name);
            // an alias leaf (RasiMesha{R1, MES}) declares no NIL of its own, so findByName
            // still answers null there - the registry fallthrough is what makes byName total
            if (null != value && !value.isNil()) return value;
        }
        return ISweEnum.byName(name, values).dignity();
    }

    public static IDignity byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).dignity();
    }

    public static IDignity byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).dignity();
    }
}
