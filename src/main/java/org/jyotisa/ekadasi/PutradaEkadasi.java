/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IPutradaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum PutradaEkadasi implements IPutradaEkadasi {
    EK4;

    @Override
    public IPutradaEkadasi[] all() {
        return values();
    }
}
