/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IMoksadaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum MoksadaEkadasi implements IMoksadaEkadasi {
    EK2;

    @Override
    public IMoksadaEkadasi[] all() {
        return values();
    }
}
