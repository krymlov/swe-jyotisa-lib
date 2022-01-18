/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IPavitropanaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum PavitropanaEkadasi implements IPavitropanaEkadasi {
    EK18;

    @Override
    public IPavitropanaEkadasi[] all() {
        return values();
    }
}
