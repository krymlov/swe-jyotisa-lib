/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaRahu;
import org.jyotisa.api.tithi.ITithiAshtami;

import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;

/**
 * 8. Ashtami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiAshtami implements ITithiAshtami {
    K8 {
        @Override
        public int uid() {
            return 23;
        }

        @Override
        public String code() {
            return K08_CD;
        }
    },
    S8;

    @Override
    public IGrahaRahu lord() {
        return RAHU;
    }

    @Override
    public ITithiAshtami[] all() {
        return values();
    }

}
