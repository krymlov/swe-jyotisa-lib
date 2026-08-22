/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.avastha;

import org.jyotisa.api.avastha.IAvastha;
import org.jyotisa.api.avastha.IAvasthaEnum;
import org.jyotisa.api.avastha.NilAvastha;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;

/**
 * The five Baaladi avasthas, and the lookup that names one from a longitude.
 * <p>
 * Built the way {@code ERasi} is: a reserved {@code NIL} at ordinal 0 that no walk reaches, and
 * one covariant accessor per member.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see IAvastha
 */
public enum EAvastha implements IAvasthaEnum {
    NIL {@Override public NilAvastha avastha() { return NilAvastha.NIL; }}, // 0  Reserved
    BALA {@Override public AvasthaBala avastha() { return AvasthaBala.AV1; }},
    KUMARA {@Override public AvasthaKumara avastha() { return AvasthaKumara.AV2; }},
    YUVA {@Override public AvasthaYuva avastha() { return AvasthaYuva.AV3; }},
    VRIDDHA {@Override public AvasthaVriddha avastha() { return AvasthaVriddha.AV4; }},
    MRITA {@Override public AvasthaMrita avastha() { return AvasthaMrita.AV5; }};

    /** how many parts a sign is cut into */
    public static final int PARTS = 5;

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IAvasthaEnum first() {
        return BALA;
    }

    @Override
    public IAvasthaEnum last() {
        return MRITA;
    }

    @Override
    public IAvasthaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IAvasthaEnum> iterator() {
        return new SweEnumIterator<>(values(), BALA.ordinal());
    }

    public static ISweEnumIterator<IAvasthaEnum> iteratorFrom(final IAvasthaEnum avastha) {
        return new SweEnumIterator<>(values(), avastha.ordinal());
    }

    public static ISweEnumIterator<IAvasthaEnum> iteratorTo(final IAvasthaEnum avastha) {
        return new SweEnumIterator<>(values(), BALA.ordinal(), avastha.ordinal());
    }

    /**
     * The avastha a longitude falls in.
     * <p>
     * Five parts of 6&deg; to a sign, running forward through an odd sign and <b>backward</b>
     * through an even one - so the first 6&deg; of an even sign is {@code MRITA} and its last
     * 6&deg; is {@code BALA}. {@code rasiFid0} is 0-based, so a classical <i>odd</i> sign is an
     * <i>even</i> index; getting that inversion the wrong way round gives a chart that is right
     * for half the zodiac.
     *
     * @return the avastha, or {@link NilAvastha#NIL} when the longitude is not a number
     */
    public static IAvastha byLongitude(final double longitude) {
        if (Double.isNaN(longitude)) return NIL.avastha();

        final int rasi0 = rasiFid0(longitude);
        final int part = (int) (rasiDegree(longitude) / (30. / PARTS));

        return byUid(rasi0 % 2 == 0 ? part + 1 : PARTS - part);
    }

    public static IAvasthaEnum byAvastha(final IAvastha avastha) {
        return ISweEnum.byCode(avastha.code(), values());
    }

    public static IAvastha byName(final String name) {
        final EAvastha[] values = values();
        for (int i = 1; i < values.length; i++) {
            IAvastha value = values[i].avastha().findByName(name);
            // an alias leaf declares no NIL of its own, so findByName still answers null there -
            // the registry fallthrough is what makes byName total
            if (null != value && !value.isNil()) return value;
        }
        return ISweEnum.byName(name, values).avastha();
    }

    public static IAvastha byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).avastha();
    }

    public static IAvastha byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).avastha();
    }

    public static IAvastha byCode(final String code) {
        return ISweEnum.byCode(code, values()).avastha();
    }
}
