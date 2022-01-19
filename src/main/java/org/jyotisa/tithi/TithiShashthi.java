/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.tithi.ITithiShashthi;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 6. Shashthi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiShashthi implements ITithiShashthi {
    S6,
    K6 {
        @Override
        public int uid() {
            return 21;
        }

        @Override
        public String code() {
            return K06_CD;
        }
    };

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public ITithiShashthi[] all() {
        return values();
    }

}
