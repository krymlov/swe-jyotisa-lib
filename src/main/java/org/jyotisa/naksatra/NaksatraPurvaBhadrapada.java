/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.naksatra.INaksatraPurvaBhadrapada;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 25. Poorvabhadrapada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraPurvaBhadrapada implements INaksatraPurvaBhadrapada {
    N25,
    PBH;

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public INaksatraPurvaBhadrapada[] all() {
        return values();
    }
}
