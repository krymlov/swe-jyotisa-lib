/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaVanija;

/**
 * 6. Vanija
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaVanija implements IKaranaVanija {
    KR6,
    VNJ;

    @Override
    public IKaranaVanija[] all() {
        return values();
    }
}
