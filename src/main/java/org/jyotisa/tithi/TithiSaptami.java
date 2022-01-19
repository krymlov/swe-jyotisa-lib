/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.tithi.ITithiSaptami;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 7. Saptami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiSaptami implements ITithiSaptami {
    S7,
    K7 {
        @Override
        public int uid() {
            return 22;
        }

        @Override
        public String code() {
            return K07_CD;
        }
    };

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public ITithiSaptami[] all() {
        return values();
    }

}
