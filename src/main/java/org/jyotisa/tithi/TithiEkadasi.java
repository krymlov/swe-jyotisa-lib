/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.tithi.ITithiEkadasi;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 11. Ekadasi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiEkadasi implements ITithiEkadasi {
    S11,
    K11 {
        @Override
        public int uid() {
            return 26;
        }

        @Override
        public String code() {
            return K11_CD;
        }
    };

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public ITithiEkadasi[] all() {
        return values();
    }

}
