/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityDeficient;

/**
 * 2 Neecha - Deficient
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityDeficient implements IDignityDeficient {
    DG2,
    DET;

    @Override
    public IDignityDeficient[] all() {
        return values();
    }
}
