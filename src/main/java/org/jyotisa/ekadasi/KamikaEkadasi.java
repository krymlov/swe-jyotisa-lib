/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IKamikaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum KamikaEkadasi implements IKamikaEkadasi {
    EK17;

    @Override
    public IKamikaEkadasi[] all() {
        return values();
    }
}
