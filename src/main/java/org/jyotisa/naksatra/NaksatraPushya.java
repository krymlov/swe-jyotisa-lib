/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.naksatra.INaksatraPushya;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 8. Pushya
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraPushya implements INaksatraPushya {
    N8,
    PUS;

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public INaksatraPushya[] all() {
        return values();
    }
}
