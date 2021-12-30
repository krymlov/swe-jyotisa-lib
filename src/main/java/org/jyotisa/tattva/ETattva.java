/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-09
 */

package org.jyotisa.tattva;

import org.jyotisa.api.tattva.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-09
 */
public enum ETattva implements ITattvaEnum {
    NIL {
        @Override
        public int fid() {
            return NIL_FID;
        }

        @Override
        public String code() {
            return NIL_CD;
        }

        @Override
        public ITattva tattva() {
            return null;
        }
    }, // 0  Reserved
    /**
     * 1. AKASHA
     */
    AKASHA() {
        @Override
        public ITattvaAkasha tattva() {
            return TattvaAkasha.TT1;
        }
    },
    /**
     * 2. AGNI
     */
    AGNI() {
        @Override
        public ITattvaAgni tattva() {
            return TattvaAgni.TT2;
        }
    },
    /**
     * 3. PRITHVI
     */
    PRITHVI() {
        @Override
        public ITattvaPrithvi tattva() {
            return TattvaPrithvi.TT3;
        }
    },
    /**
     * 4. VAYU
     */
    VAYU() {
        @Override
        public ITattvaVayu tattva() {
            return TattvaVayu.TT4;
        }
    },
    /**
     * 5. JALA
     */
    JALA() {
        @Override
        public ITattvaJala tattva() {
            return TattvaJala.TT5;
        }
    };

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public ITattvaEnum first() {
        return AKASHA;
    }

    @Override
    public ITattvaEnum last() {
        return JALA;
    }

    @Override
    public ITattvaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<ITattvaEnum> iterator() {
        return new SweEnumIterator<>(values(), AKASHA.uid());
    }

    public static ISweEnumIterator<ITattvaEnum> iteratorFrom(final ITattvaEnum tattva) {
        return new SweEnumIterator<>(values(), tattva.uid());
    }

    public static ISweEnumIterator<ITattvaEnum> iteratorTo(final ITattvaEnum tattva) {
        return new SweEnumIterator<>(values(), AKASHA.uid(), tattva.uid());
    }

    public static ITattvaEnum byTattva(final ITattva tattva) {
        return ISweEnum.byCode(tattva.code(), values());
    }

    public static ITattva byName(final String name) {
        final ETattva[] values = values();
        for (int i = 1; i < values.length; i++) {
            ITattva value = values[i].tattva().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).tattva();
    }

    public static ITattva byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).tattva();
    }

    public static ITattva byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).tattva();
    }
}
