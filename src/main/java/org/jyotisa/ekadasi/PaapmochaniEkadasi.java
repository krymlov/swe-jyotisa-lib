/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IPaapmochaniEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum PaapmochaniEkadasi implements IPaapmochaniEkadasi {
    EK9;

    @Override
    public IPaapmochaniEkadasi[] all() {
        return values();
    }
}
