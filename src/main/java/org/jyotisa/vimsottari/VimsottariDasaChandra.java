/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.vimsottari.IVimsottariDasaChandra;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaChandra implements IVimsottariDasaChandra {
    VD2,
    CHVD;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public IVimsottariDasaChandra[] all() {
        return values();
    }
}
