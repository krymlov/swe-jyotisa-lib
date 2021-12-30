/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityUccha;

/**
 * 11 Uccha - Exaltation
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityUccha implements IDignityUccha {
    DG11,
    UCC;

    @Override
    public IDignityUccha[] all() {
        return values();
    }
}
