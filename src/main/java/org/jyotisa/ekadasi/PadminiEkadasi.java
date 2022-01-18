/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IPadminiEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum PadminiEkadasi implements IPadminiEkadasi {
    EK25;

    @Override
    public IPadminiEkadasi[] all() {
        return values();
    }
}
