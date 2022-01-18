/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.ekadasi;

import org.jyotisa.api.ekadasi.IYoginiEkadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public enum YoginiEkadasi implements IYoginiEkadasi {
    EK15;

    @Override
    public IYoginiEkadasi[] all() {
        return values();
    }
}