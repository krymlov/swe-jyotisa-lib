/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IParamaEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum ParamaEkadasi implements IParamaEkadasi {
    EK26;

    @Override
    public IParamaEkadasi[] all() {
        return values();
    }
}
