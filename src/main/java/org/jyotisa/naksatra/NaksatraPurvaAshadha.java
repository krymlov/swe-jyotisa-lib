/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.naksatra.INaksatraPurvaAshadha;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 20. Poorvashada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraPurvaAshadha implements INaksatraPurvaAshadha {
    N20,
    PSH;

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public INaksatraPurvaAshadha[] all() {
        return values();
    }
}
