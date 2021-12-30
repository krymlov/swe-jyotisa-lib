/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.naksatra.INaksatraChitra;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 14. Chitra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraChitra implements INaksatraChitra {
    N14,
    CHT;

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public INaksatraChitra[] all() {
        return values();
    }
}
