/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IMohiniEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum MohiniEkadasi implements IMohiniEkadasi {
    EK12;

    @Override
    public IMohiniEkadasi[] all() {
        return values();
    }
}
