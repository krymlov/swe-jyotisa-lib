/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.arudha;

import org.jyotisa.api.arudha.IArudhaPadaA3;

/**
 * 3.  the perceived siblings and effort
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum ArudhaPadaA3 implements IArudhaPadaA3 {
    A3;

    @Override
    public IArudhaPadaA3[] all() {
        return values();
    }
}
