/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IAparaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum AparaEkadasi implements IAparaEkadasi {
    EK13;

    @Override
    public IAparaEkadasi[] all() {
        return values();
    }
}
