/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.tithi.ITithiDashami;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * 10. Dashami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiDashami implements ITithiDashami {
    K10 {
        @Override
        public int uid() {
            return 25;
        }

        @Override
        public String code() {
            return K10_CD;
        }
    },
    S10;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public ITithiDashami[] all() {
        return values();
    }

}
