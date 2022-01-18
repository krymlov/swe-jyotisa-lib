/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IVijayaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum VijayaEkadasi implements IVijayaEkadasi {
    EK7;

    @Override
    public IVijayaEkadasi[] all() {
        return values();
    }
}
