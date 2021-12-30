/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaBalava;

/**
 * 2. Balava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaBalava implements IKaranaBalava {
    KR2,
    BLV;

    @Override
    public IKaranaBalava[] all() {
        return values();
    }
}
