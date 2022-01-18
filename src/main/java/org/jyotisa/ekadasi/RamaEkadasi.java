/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IRamaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum RamaEkadasi implements IRamaEkadasi {
    EK23;

    @Override
    public IRamaEkadasi[] all() {
        return values();
    }
}
