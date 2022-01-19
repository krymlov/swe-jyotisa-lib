/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaRahu;
import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.tithi.ITithiPoornima;

import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;
import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 15. Purnima
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiPoornima implements ITithiPoornima {
    S15 {
        @Override
        public IGrahaShani lord() {
            return SHANI;
        }
    },
    K15 {
        @Override
        public int uid() {
            return 30;
        }

        @Override
        public IGrahaRahu lord() {
            return RAHU;
        }

        @Override
        public String code() {
            return K15_CD;
        }
    };

    @Override
    public ITithiPoornima[] all() {
        return values();
    }

}
