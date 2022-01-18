/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IKamadaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum KamadaEkadasi implements IKamadaEkadasi {
    EK10;

    @Override
    public IKamadaEkadasi[] all() {
        return values();
    }
}
