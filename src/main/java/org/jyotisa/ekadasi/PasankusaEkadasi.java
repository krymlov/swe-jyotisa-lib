/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IPasankusaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum PasankusaEkadasi implements IPasankusaEkadasi {
    EK22;

    @Override
    public IPasankusaEkadasi[] all() {
        return values();
    }
}
