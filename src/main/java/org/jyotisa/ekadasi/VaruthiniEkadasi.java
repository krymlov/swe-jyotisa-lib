/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IVaruthiniEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum VaruthiniEkadasi implements IVaruthiniEkadasi {
    EK11;

    @Override
    public IVaruthiniEkadasi[] all() {
        return values();
    }
}
