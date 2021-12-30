/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityMitra;

/**
 * 6 Mitra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityMitra implements IDignityMitra {
    DG6,
    MIR;

    @Override
    public IDignityMitra[] all() {
        return values();
    }
}
