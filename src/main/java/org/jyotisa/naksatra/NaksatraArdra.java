/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaRahu;
import org.jyotisa.api.naksatra.INaksatraArdra;

import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;

/**
 * 6. Ardra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraArdra implements INaksatraArdra {
    N6,
    ARD;

    @Override
    public IGrahaRahu lord() {
        return RAHU;
    }

    @Override
    public INaksatraArdra[] all() {
        return values();
    }
}
