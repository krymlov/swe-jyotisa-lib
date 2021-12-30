/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.vimsottari.IVimsottariDasaGuru;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 5. Guru vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaGuru implements IVimsottariDasaGuru {
    VD5,
    GUVD;

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public IVimsottariDasaGuru[] all() {
        return values();
    }
}
