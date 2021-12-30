/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.tithi.ITithiTritiya;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 3. Tritiya
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiTritiya implements ITithiTritiya {
    K3 {
        @Override
        public int uid() {
            return 18;
        }

        @Override
        public String code() {
            return K03_CD;
        }
    },
    S3;

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public ITithiTritiya[] all() {
        return values();
    }

}
