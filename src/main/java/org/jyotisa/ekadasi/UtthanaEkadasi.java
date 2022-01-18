/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IUtthanaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum UtthanaEkadasi implements IUtthanaEkadasi {
    EK24;

    @Override
    public IUtthanaEkadasi[] all() {
        return values();
    }
}
