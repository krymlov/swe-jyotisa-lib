/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.naksatra.INaksatraUttaraBhadrapada;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 26. Uttarabhadrapada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraUttaraBhadrapada implements INaksatraUttaraBhadrapada {
    N26,
    UBH;

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public INaksatraUttaraBhadrapada[] all() {
        return values();
    }
}
