/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaKaala;

/**
 * 6. Kaala
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaKaala implements IUpagrahaKaala {
    UG6,
    KAA;

    @Override
    public IUpagrahaKaala[] all() {
        return values();
    }
}
