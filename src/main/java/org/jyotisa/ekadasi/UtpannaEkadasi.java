/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IUtpannaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum UtpannaEkadasi implements IUtpannaEkadasi {
    EK1;

    @Override
    public IUtpannaEkadasi[] all() {
        return values();
    }
}
