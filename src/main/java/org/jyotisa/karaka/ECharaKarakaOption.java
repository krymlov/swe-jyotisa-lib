/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.karaka;

import org.jyotisa.api.karaka.ICharaKarakaOption;
import org.swisseph.api.ISweEnum;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public enum ECharaKarakaOption implements ICharaKarakaOption {
    SEVEN_KARAKAS,
    EIGHT_KARAKAS {
        @Override
        public int fid() {
            return 8;
        }

        @Override
        public String code() {
            return CK8_CD;
        }
    };

    @Override
    public int uid() {
        return ordinal();
    }

    public static ICharaKarakaOption byFid(final int fid) {
        return ISweEnum.byFid(fid, values());
    }

    public static ICharaKarakaOption byUid(final int uid) {
        return ISweEnum.byUid(uid, values());
    }

    public static ICharaKarakaOption byCode(final String code) {
        return ISweEnum.byCode(code, values());
    }
}
