/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.naksatra.INaksatraPunarvasu;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 7. Punarvasu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraPunarvasu implements INaksatraPunarvasu {
    N7,
    PUN;

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public INaksatraPunarvasu[] all() {
        return values();
    }
}
