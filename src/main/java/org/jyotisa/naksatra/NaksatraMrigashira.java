/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.naksatra.INaksatraMrigashira;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 5. Mrigashira
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraMrigashira implements INaksatraMrigashira {
    N5,
    MRG;

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public INaksatraMrigashira[] all() {
        return values();
    }
}
