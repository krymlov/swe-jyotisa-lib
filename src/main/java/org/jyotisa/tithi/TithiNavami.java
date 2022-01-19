/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.tithi.ITithiNavami;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * 9. Navami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiNavami implements ITithiNavami {
    S9,
    K9 {
        @Override
        public int uid() {
            return 24;
        }

        @Override
        public String code() {
            return K09_CD;
        }
    };

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public ITithiNavami[] all() {
        return values();
    }

}
