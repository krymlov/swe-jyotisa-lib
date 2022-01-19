/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.tithi.ITithiPanchami;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 5. Panchami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiPanchami implements ITithiPanchami {
    S5,
    K5 {
        @Override
        public int uid() {
            return 20;
        }

        @Override
        public String code() {
            return K05_CD;
        }
    };

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public ITithiPanchami[] all() {
        return values();
    }

}
