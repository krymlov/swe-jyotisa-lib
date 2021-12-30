/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityExcellent;

/**
 * 10 Uccha - Excellent
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityExcellent implements IDignityExcellent {
    DG10,
    EXT;

    @Override
    public IDignityExcellent[] all() {
        return values();
    }
}
