/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaBhava;

/**
 * 1. Bhava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaBhava implements ILagnaBhava {
    L1,
    BL;

    @Override
    public ILagnaBhava[] all() {
        return values();
    }
}
