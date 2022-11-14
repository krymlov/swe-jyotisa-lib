/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-08
*/

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author  Yura Krymlov
 * @version 1.1, 2019-08
 */
public enum EUpagraha implements IUpagrahaEnum {
    NIL {
        @Override public int fid() { return NIL_FID; }
        @Override public String code() { return NIL_CD; }
        @Override public IUpagraha upagraha() { return null; }
    }, // 0  Reserved
    DHUMA {@Override public IUpagrahaDhuma upagraha() { return UpagrahaDhuma.UG1; }},
    VYATIPAATA {@Override public IUpagrahaVyatipaata upagraha() { return UpagrahaVyatipaata.UG2; }},
    PARIVESHA {@Override public IUpagrahaParivesha upagraha() { return UpagrahaParivesha.UG3; }},
    INDRACHAAPA {@Override public IUpagrahaIndrachaapa upagraha() { return UpagrahaIndrachaapa.UG4; }},
    UPAKETU {@Override public IUpagrahaUpaketu upagraha() { return UpagrahaUpaketu.UG5; }},
    KAALA {@Override public IUpagrahaKaala upagraha() { return UpagrahaKaala.UG6; }},
    MRITYU {@Override public IUpagrahaMrityu upagraha() { return UpagrahaMrityu.UG7; }},
    ARTHAPRAHAARA {@Override public IUpagrahaArthaprahaara upagraha() { return UpagrahaArthaprahaara.UG8; }},
    YAMAGHANTAKA {@Override public IUpagrahaYamaghantaka upagraha() { return UpagrahaYamaghantaka.UG9; }},
    GULIKA {@Override public IUpagrahaGulika upagraha() { return UpagrahaGulika.UG10; }},
    MAANDI {@Override public IUpagrahaMaandi upagraha() { return UpagrahaMaandi.UG11; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IUpagrahaEnum first() {
        return DHUMA;
    }

    @Override
    public IUpagrahaEnum last() {
        return MAANDI;
    }

    @Override
    public IUpagrahaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IUpagrahaEnum> iterator() {
        return new SweEnumIterator<>(values(), DHUMA.ordinal());
    }

    public static ISweEnumIterator<IUpagrahaEnum> iteratorFrom(final IUpagrahaEnum upagraha) {
        return new SweEnumIterator<>(values(), upagraha.ordinal());
    }

    public static ISweEnumIterator<IUpagrahaEnum> iteratorTo(IUpagrahaEnum upagraha) {
        return new SweEnumIterator<>(values(), DHUMA.ordinal(), upagraha.ordinal());
    }

    public static ISweEnumIterator<IUpagrahaEnum> iterator(IUpagrahaEnum upagrahaFrom, IUpagrahaEnum upagrahaTo) {
        return new SweEnumIterator<>(values(), upagrahaFrom.ordinal(), upagrahaTo.ordinal());
    }

    public static IUpagrahaEnum byUpagraha(final IUpagraha upagraha) {
        return ISweEnum.byCode(upagraha.code(), values());
    }

    public static IUpagraha byName(final String name) {
        final EUpagraha[] values = values();
        for (int i = 1; i < values.length; i++) {
            IUpagraha value = values[i].upagraha().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).upagraha();
    }

    public static IUpagraha byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).upagraha();
    }

    public static IUpagraha byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).upagraha();
    }
}
