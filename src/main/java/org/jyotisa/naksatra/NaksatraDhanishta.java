/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.naksatra.INaksatraDhanishta;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 23. Dhanishta
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraDhanishta implements INaksatraDhanishta {
    N23,
    DHA;

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public INaksatraDhanishta[] all() {
        return values();
    }
}
