/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.arudha;

import org.jyotisa.api.arudha.IArudhaPadaA12;

/**
 * 12.  Upapada Lagna - the perceived marriage and expenditure
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum ArudhaPadaA12 implements IArudhaPadaA12 {
    A12,
    UL;

    @Override
    public IArudhaPadaA12[] all() {
        return values();
    }
}
