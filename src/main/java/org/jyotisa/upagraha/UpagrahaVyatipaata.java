/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaVyatipaata;

/**
 * 2. Vyatipaata
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaVyatipaata implements IUpagrahaVyatipaata {
    UG2,
    VYA;

    @Override
    public IUpagrahaVyatipaata[] all() {
        return values();
    }
}
