/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.tithi.ITithiChaturdasi;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 14. Chaturdashi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiChaturdasi implements ITithiChaturdasi {
    K14 {
        @Override
        public int uid() {
            return 29;
        }

        @Override
        public String code() {
            return K14_CD;
        }
    },
    S14;

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public ITithiChaturdasi[] all() {
        return values();
    }

}
