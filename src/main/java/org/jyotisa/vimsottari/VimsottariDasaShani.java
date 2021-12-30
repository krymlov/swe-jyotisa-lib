/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.vimsottari.IVimsottariDasaShani;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 6. Shani vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaShani implements IVimsottariDasaShani {
    VD6,
    SAVD;

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public IVimsottariDasaShani[] all() {
        return values();
    }
}
