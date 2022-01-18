/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IAnnadaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum AnnadaEkadasi implements IAnnadaEkadasi {
    EK19;

    @Override
    public IAnnadaEkadasi[] all() {
        return values();
    }
}
