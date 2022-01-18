/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IAmalakiEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum AmalakiEkadasi implements IAmalakiEkadasi {
    EK8;

    @Override
    public IAmalakiEkadasi[] all() {
        return values();
    }
}
