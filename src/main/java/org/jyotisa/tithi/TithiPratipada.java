/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.tithi.ITithiPratipada;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * 1. Pratipada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiPratipada implements ITithiPratipada {
    K1 {
        @Override
        public int uid() {
            return 16;
        }

        @Override
        public String code() {
            return K01_CD;
        }
    },
    S1;

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public ITithiPratipada[] all() {
        return values();
    }

}
