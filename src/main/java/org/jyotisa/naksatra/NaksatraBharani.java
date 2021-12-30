/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.naksatra.INaksatraBharani;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 2 Bharani
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraBharani implements INaksatraBharani {
    N2,
    BHA;

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public INaksatraBharani[] all() {
        return values();
    }
}
