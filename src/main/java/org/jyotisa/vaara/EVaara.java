/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.vaara;


import org.jyotisa.api.vaara.*;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

import java.util.Calendar;

import static org.swisseph.api.ISweConstants.i1;

/**
 * Vaara - week day – Sunday, Monday etc
 *
 * @author Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum EVaara implements IVaaraEnum {
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
        public IVaara vaara() {
            return null;
        }
    }, // 0  Reserved
    /**
     * 1. Sūryavāra
     */
    SURYA_VAARA {
        @Override
        public IVaaraSurya vaara() {
            return VaaraSurya.VR1;
        }
    },
    /**
     * 2. Chandravaara
     */
    CHANDRA_VAARA {
        @Override
        public IVaaraChandra vaara() {
            return VaaraChandra.VR2;
        }
    },
    /**
     * 3. Mangalavaara
     */
    MANGALA_VAARA {
        @Override
        public IVaaraMangala vaara() {
            return VaaraMangala.VR3;
        }
    },
    /**
     * 4. Budhavaara
     */
    BUDHA_VAARA {
        @Override
        public IVaaraBudha vaara() {
            return VaaraBudha.VR4;
        }
    },
    /**
     * 5. Guruvaara
     */
    GURU_VAARA {
        @Override
        public IVaaraGuru vaara() {
            return VaaraGuru.VR5;
        }
    },
    /**
     * 6. Shukravaara
     */
    SHUKRA_VAARA {
        @Override
        public IVaaraShukra vaara() {
            return VaaraShukra.VR6;
        }
    },
    /**
     * 7. Shanivaara
     */
    SHANI_VAARA {
        @Override
        public IVaaraShani vaara() {
            return VaaraShani.VR7;
        }
    };

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public IVaaraEnum first() {
        return SURYA_VAARA;
    }

    @Override
    public IVaaraEnum last() {
        return SHANI_VAARA;
    }

    @Override
    public IVaaraEnum[] all() {
        return values();
    }

    /**
     * Sunday is represented by 0, Saturday by 6. Any discontinuity
     * in the sequence of weekdays is <b>not</b> taken into account!
     * <B>Attention: the numbers are different from the numbers returned by the {@link Calendar}</B>
     */
    public static IVaara byDayOfWeek(final int sweDayOfWeek) {
        return EVaara.byUid(i1 + sweDayOfWeek);
    }

    public static ISweEnumIterator<IVaaraEnum> iterator() {
        return new SweEnumIterator<>(values(), SURYA_VAARA.uid());
    }

    public static ISweEnumIterator<IVaaraEnum> iteratorFrom(final IVaaraEnum vaara) {
        return new SweEnumIterator<>(values(), vaara.uid());
    }

    public static ISweEnumIterator<IVaaraEnum> iteratorTo(final IVaaraEnum vaara) {
        return new SweEnumIterator<>(values(), SURYA_VAARA.uid(), vaara.uid());
    }

    public static IVaaraEnum byVaara(final IVaara vaara) {
        return ISweEnum.byCode(vaara.code(), values());
    }

    public static IVaara byName(final String name) {
        final EVaara[] values = values();
        for (int i = 1; i < values.length; i++) {
            IVaara value = values[i].vaara().findByName(name);
            if (null != value) return value;
        }
        return ISweEnum.byName(name, values).vaara();
    }

    public static IVaara byIndex(final int idx) {
        return ISweEnum.byIndex(idx, values()).vaara();
    }

    public static IVaara byUid(final int uid) {
        return ISweEnum.byUid(uid, values()).vaara();
    }
}
