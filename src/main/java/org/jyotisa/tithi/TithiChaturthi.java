/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.tithi;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.tithi.ITithiChaturthi;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 4. Chaturthi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TithiChaturthi implements ITithiChaturthi {
    K4 {
        @Override
        public int uid() {
            return 19;
        }

        @Override
        public String code() {
            return K04_CD;
        }
    },
    S4;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public ITithiChaturthi[] all() {
        return values();
    }

}
