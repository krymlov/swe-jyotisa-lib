/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IJayaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum JayaEkadasi implements IJayaEkadasi {
    EK6;

    @Override
    public IJayaEkadasi[] all() {
        return values();
    }
}
