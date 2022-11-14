/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-09
 */

package org.jyotisa.karaka;

import org.jyotisa.api.karaka.ICharaKaraka;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumIterator;
import org.swisseph.app.SweEnumIterator;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-09
 */
public enum ECharaKaraka implements ICharaKaraka {
    // 0  Reserved
    NIL {
        @Override
        public String code() {
            return NIL_CD;
        }
    },
    /* 1. Atma Karaka (AK) (lagna, 1st house) */
    ATMA_KARAKA {
        @Override
        public String code() {
            return AK_CD;
        }
    },
    /* 2. Amatya Karaka (AmK) (2nd and 10th) */
    AMATYA_KARAKA {
        @Override
        public String code() {
            return AMK_CD;
        }
    },
    /* 3. Bhratri Karaka (BK) (3rd houses) */
    BHRATRI_KARAKA {
        @Override
        public String code() {
            return BK_CD;
        }
    },
    /* 4. Matri Karaka (MK) (the 4th house) */
    MATRI_KARAKA {
        @Override
        public String code() {
            return MK_CD;
        }
    },
    /* 5. Pitri Karaka (PiK) (9th house) */
    PITRI_KARAKA {
        @Override
        public String code() {
            return PIK_CD;
        }
    },
    /* 6. Putra Karaka (PK) (the 5th house) */
    PUTRA_KARAKA {
        @Override
        public String code() {
            return PK_CD;
        }
    },
    /* 7. Gnati Karaka (GK) (6, 8 and 12) */
    GNATI_KARAKA {
        @Override
        public String code() {
            return GK_CD;
        }
    },
    /* 8. Dara Karaka (DK) (7th house) */
    DARA_KARAKA {
        @Override
        public String code() {
            return DK_CD;
        }
    };

    @Override
    public int fid() {
        return ordinal();
    }

    @Override
    public int uid() {
        return ordinal();
    }

    @Override
    public ICharaKaraka first() {
        return ATMA_KARAKA;
    }

    @Override
    public ICharaKaraka last() {
        return DARA_KARAKA;
    }

    @Override
    public ICharaKaraka[] all() {
        return values();
    }

    public static ISweEnumIterator<ICharaKaraka> iterator() {
        return new SweEnumIterator<>(values(), ATMA_KARAKA.ordinal());
    }

    public static ISweEnumIterator<ICharaKaraka> iteratorFrom(final ICharaKaraka karaka) {
        return new SweEnumIterator<>(values(), karaka.ordinal());
    }

    public static ISweEnumIterator<ICharaKaraka> iteratorTo(final ICharaKaraka karaka) {
        return new SweEnumIterator<>(values(), ATMA_KARAKA.ordinal(), karaka.ordinal());
    }

    public static ISweEnumIterator<ICharaKaraka> iterator(final ICharaKaraka karakaFrom, final ICharaKaraka karakaTo) {
        return new SweEnumIterator<>(values(), karakaFrom.ordinal(), karakaTo.ordinal());
    }

    public static ICharaKaraka byCode(String code) {
        return ISweEnum.byCode(code, values());
    }

    public static ICharaKaraka byIndex(final int index) {
        return ISweEnum.byIndex(index, values());
    }

    public static ICharaKaraka byUid(int uid) {
        return ISweEnum.byUid(uid, values());
    }
}
