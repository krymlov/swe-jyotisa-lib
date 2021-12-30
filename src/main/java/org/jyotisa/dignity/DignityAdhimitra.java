/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityAdhimitra;

/**
 * 7 Adhimitra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityAdhimitra implements IDignityAdhimitra {
    DG7,
    ADM;

    @Override
    public IDignityAdhimitra[] all() {
        return values();
    }
}
