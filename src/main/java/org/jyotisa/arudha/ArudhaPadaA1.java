/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.arudha;

import org.jyotisa.api.arudha.IArudhaPadaA1;

/**
 * 1.  Arudha Lagna - the perceived self
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum ArudhaPadaA1 implements IArudhaPadaA1 {
    A1,
    AL;

    @Override
    public IArudhaPadaA1[] all() {
        return values();
    }
}
