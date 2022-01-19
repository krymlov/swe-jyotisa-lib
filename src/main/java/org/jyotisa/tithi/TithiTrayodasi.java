/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.tithi.ITithiTrayodasi;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 13. Trayodashi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiTrayodasi implements ITithiTrayodasi {
    S13,
    K13 {
        @Override
        public int uid() {
            return 28;
        }

        @Override
        public String code() {
            return K13_CD;
        }
    };

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public ITithiTrayodasi[] all() {
        return values();
    }

}
