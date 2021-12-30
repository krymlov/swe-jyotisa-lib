/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.naksatra.INaksatraPurvaPhalguni;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 11. Poorva Phalguni
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraPurvaPhalguni implements INaksatraPurvaPhalguni {
    N11,
    PPH;

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public INaksatraPurvaPhalguni[] all() {
        return values();
    }
}
