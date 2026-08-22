/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.arudha;

import org.jyotisa.api.arudha.IArudhaPadaA5;

/**
 * 5.  the perceived children and learning
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum ArudhaPadaA5 implements IArudhaPadaA5 {
    A5;

    @Override
    public IArudhaPadaA5[] all() {
        return values();
    }
}
