/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum ELagna implements ILagnaEnum {
    JANMA_LAGNA {
        @Override
        public ILagnaJanma lagna() {
            return LagnaJanma.L0;
        }
    },
    BHAVA_LAGNA {
        @Override
        public ILagnaBhava lagna() {
            return LagnaBhava.L1;
        }
    },
    HORA_LAGNA {
        @Override
        public ILagnaHora lagna() {
            return LagnaHora.L2;
        }
    },
    GHATI_LAGNA {
        @Override
        public ILagnaGhati lagna() {
            return LagnaGhati.L3;
        }
    },
    VIGHATI_LAGNA {
        @Override
        public ILagnaVighati lagna() {
            return LagnaVighati.L4;
        }
    },
    VARNADA_LAGNA {
        @Override
        public ILagnaVarnada lagna() {
            return LagnaVarnada.L5;
        }
    },
    SREE_LAGNA {
        @Override
        public ILagnaSree lagna() {
            return LagnaSree.L6;
        }
    },
    PRANAPADA_LAGNA {
        @Override
        public ILagnaPranapada lagna() {
            return LagnaPranapada.L7;
        }
    },
    INDU_LAGNA {
        @Override
        public ILagnaIndu lagna() {
            return LagnaIndu.L8;
        }
    };

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public ILagnaEnum first() {
        return JANMA_LAGNA;
    }

    @Override
    public ILagnaEnum last() {
        return INDU_LAGNA;
    }

    @Override
    public ILagnaEnum[] all() {
        return values();
    }

    public static ISweEnumIterator<ILagnaEnum> iterator() {
        return new SweEnumIterator<>(values(), JANMA_LAGNA.uid());
    }

    public static ISweEnumIterator<ILagnaEnum> iteratorFrom(final ILagnaEnum lagna) {
        return new SweEnumIterator<>(values(), lagna.uid());
    }

    public static ISweEnumIterator<ILagnaEnum> iteratorTo(final ILagnaEnum lagna) {
        return new SweEnumIterator<>(values(), JANMA_LAGNA.uid(), lagna.uid());
    }

    public static ILagnaEnum byLagna(final ILagna lagna) {
        return ISweEnum.byCode(lagna.code(), values());
    }

    public static ILagna byName(final String name) {
        final ELagna[] values = values();
        for (int i = 1; i < values.length; i++) {
            ILagna value = values[i].lagna().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).lagna();
    }

    public static ILagna byCode(final String code) {
        return ISweEnum.byCode(code, values()).lagna();
    }

    public static ILagna byIndex(final int index) {
        return ISweEnum.byIndex(index, values()).lagna();
    }

    public static ILagna byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).lagna();
    }
}
