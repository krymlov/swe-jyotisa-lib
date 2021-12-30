/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.naksatra.INaksatraRohini;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * 4 Rohini
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraRohini implements INaksatraRohini {
    N4,
    ROH;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public INaksatraRohini[] all() {
        return values();
    }
}
