/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaKaulava;

/**
 * 3. Kaulava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaKaulava implements IKaranaKaulava {
    KR3,
    KLV;

    @Override
    public IKaranaKaulava[] all() {
        return values();
    }
}
