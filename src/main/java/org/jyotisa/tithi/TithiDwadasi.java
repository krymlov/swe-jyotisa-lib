/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.tithi.ITithiDwadasi;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 12. Dwadashi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiDwadasi implements ITithiDwadasi {
    K12 {
        @Override
        public int uid() {
            return 27;
        }

        @Override
        public String code() {
            return K12_CD;
        }
    },
    S12;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public ITithiDwadasi[] all() {
        return values();
    }

}
