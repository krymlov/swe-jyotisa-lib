/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IParsvaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum ParsvaEkadasi implements IParsvaEkadasi {
    EK20;

    @Override
    public IParsvaEkadasi[] all() {
        return values();
    }
}
