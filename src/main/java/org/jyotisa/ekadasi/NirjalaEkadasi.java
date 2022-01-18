/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.INirjalaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum NirjalaEkadasi implements INirjalaEkadasi {
    EK14;

    @Override
    public INirjalaEkadasi[] all() {
        return values();
    }
}
