/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-08
*/

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author  Yura Krymlov
 * @version 1.1, 2019-08
 *
 */
public enum EBhava implements IBhavaEnum {
    NIL {
        @Override public int fid() { return 0; }
        @Override public String code() { return NIL_CD; }
        @Override public IBhava bhava() { return null; }
    }, // 0  Reserved
    TANU {@Override public IBhavaTanu bhava() { return BhavaTanu.B1; }},
    DHANA {@Override public IBhavaDhana bhava() { return BhavaDhana.B2; }},
    BHRATRI {@Override public IBhavaBhratri bhava() { return BhavaBhratri.B3; }},
    MATRI {@Override public IBhavaMatri bhava() { return BhavaMatri.B4; }},
    PUTRA {@Override public IBhavaPutra bhava() { return BhavaPutra.B5; }},
    ARI {@Override public IBhavaAri bhava() { return BhavaAri.B6; }},
    KAMA {@Override public IBhavaKama bhava() { return BhavaKama.B7; }},
    AYUR {@Override public IBhavaAyur bhava() { return BhavaAyur.B8; }},
    DHARMA {@Override public IBhavaDharma bhava() { return BhavaDharma.B9; }},
    KARMA {@Override public IBhavaKarma bhava() { return BhavaKarma.B10; }},
    LABHA {@Override public IBhavaLabha bhava() { return BhavaLabha.B11; }},
    VYAYA {@Override public IBhavaVyaya bhava() { return BhavaVyaya.B12; }};

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IBhavaEnum first() {
        return TANU;
    }

    @Override
    public IBhavaEnum last() {
        return VYAYA;
    }

    @Override
    public IBhavaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<IBhavaEnum> iterator() {
        return new SweEnumIterator<>(values(), TANU.ordinal());
    }

    public static ISweEnumIterator<IBhavaEnum> iteratorFrom(final IBhavaEnum bhava) {
        return new SweEnumIterator<>(values(), bhava.ordinal());
    }

    public static ISweEnumIterator<IBhavaEnum> iteratorTo(final IBhavaEnum bhava) {
        return new SweEnumIterator<>(values(), TANU.ordinal(), bhava.ordinal());
    }

    public static ISweEnumIterator<IBhavaEnum> iterator(final IBhavaEnum bhavaFrom, final IBhavaEnum bhavaTo) {
        return new SweEnumIterator<>(values(), bhavaFrom.ordinal(), bhavaTo.ordinal());
    }

    public static IBhavaEnum byBhava(final IBhava bhava) {
        return ISweEnum.byCode(bhava.code(), values());
    }

    public static IBhava byName(final String name) {
        final EBhava[] values = values();
        for (int i = 1; i < values.length; i++) {
            IBhava value = values[i].bhava().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).bhava();
    }

    public static IBhava byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).bhava();
    }

    public static IBhava byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).bhava();
    }
}
