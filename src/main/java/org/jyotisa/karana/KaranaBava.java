/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaBava;

/**
 * 1. Bava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaBava implements IKaranaBava {
    KR1,
    BAV;

    @Override
    public IKaranaBava[] all() {
        return values();
    }
}
