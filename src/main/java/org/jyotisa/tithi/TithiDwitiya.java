/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.tithi.ITithiDwitiya;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * 2. Dwitiya
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiDwitiya implements ITithiDwitiya {
    K2 {
        @Override
        public int uid() {
            return 17;
        }

        @Override
        public String code() {
            return K02_CD;
        }
    },
    S2;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public ITithiDwitiya[] all() {
        return values();
    }

}
