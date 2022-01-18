/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IIndiraEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum IndiraEkadasi implements IIndiraEkadasi {
    EK21;

    @Override
    public IIndiraEkadasi[] all() {
        return values();
    }
}
