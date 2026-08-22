/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.arudha;

import org.jyotisa.api.arudha.IArudhaPada;
import org.jyotisa.api.arudha.IArudhaPadaEnum;
import org.jyotisa.api.arudha.NilArudhaPada;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * The registry of the twelve arudha padas, {@code A1} through {@code A12} - the arudha of the
 * first bhava being the <b>Arudha Lagna</b> and that of the twelfth the <b>Upapada Lagna</b>.
 * <p>
 * Built the way {@code ERasi} is: a reserved {@code NIL} at ordinal 0 that no walk reaches, and
 * one covariant accessor per member.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see org.jyotisa.api.arudha.IArudhaPadas
 */
public enum EArudhaPada implements IArudhaPadaEnum {
    NIL {@Override public NilArudhaPada arudhaPada() { return NilArudhaPada.NIL; }}, // 0  Reserved
    A1 {@Override public ArudhaPadaA1 arudhaPada() { return ArudhaPadaA1.A1; }},
    A2 {@Override public ArudhaPadaA2 arudhaPada() { return ArudhaPadaA2.A2; }},
    A3 {@Override public ArudhaPadaA3 arudhaPada() { return ArudhaPadaA3.A3; }},
    A4 {@Override public ArudhaPadaA4 arudhaPada() { return ArudhaPadaA4.A4; }},
    A5 {@Override public ArudhaPadaA5 arudhaPada() { return ArudhaPadaA5.A5; }},
    A6 {@Override public ArudhaPadaA6 arudhaPada() { return ArudhaPadaA6.A6; }},
    A7 {@Override public ArudhaPadaA7 arudhaPada() { return ArudhaPadaA7.A7; }},
    A8 {@Override public ArudhaPadaA8 arudhaPada() { return ArudhaPadaA8.A8; }},
    A9 {@Override public ArudhaPadaA9 arudhaPada() { return ArudhaPadaA9.A9; }},
    A10 {@Override public ArudhaPadaA10 arudhaPada() { return ArudhaPadaA10.A10; }},
    A11 {@Override public ArudhaPadaA11 arudhaPada() { return ArudhaPadaA11.A11; }},
    A12 {@Override public ArudhaPadaA12 arudhaPada() { return ArudhaPadaA12.A12; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IArudhaPadaEnum first() {
        return A1;
    }

    @Override
    public IArudhaPadaEnum last() {
        return A12;
    }

    @Override
    public IArudhaPadaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IArudhaPadaEnum> iterator() {
        return new SweEnumIterator<>(values(), A1.ordinal());
    }

    public static ISweEnumIterator<IArudhaPadaEnum> iteratorFrom(final IArudhaPadaEnum pada) {
        return new SweEnumIterator<>(values(), pada.ordinal());
    }

    public static ISweEnumIterator<IArudhaPadaEnum> iteratorTo(final IArudhaPadaEnum pada) {
        return new SweEnumIterator<>(values(), A1.ordinal(), pada.ordinal());
    }

    public static IArudhaPadaEnum byArudhaPada(final IArudhaPada pada) {
        return ISweEnum.byCode(pada.code(), values());
    }

    public static IArudhaPada byName(final String name) {
        final EArudhaPada[] values = values();
        for (int i = 1; i < values.length; i++) {
            IArudhaPada value = values[i].arudhaPada().findByName(name);
            // an alias leaf (ArudhaPadaA1{A1, AL}) declares no NIL of its own, so findByName
            // still answers null there - the registry fallthrough is what makes byName total
            if (null != value && !value.isNil()) return value;
        }
        return ISweEnum.byName(name, values).arudhaPada();
    }

    public static IArudhaPada byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).arudhaPada();
    }

    public static IArudhaPada byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).arudhaPada();
    }

    public static IArudhaPada byCode(final String code) {
        return ISweEnum.byCode(code, values()).arudhaPada();
    }
}
