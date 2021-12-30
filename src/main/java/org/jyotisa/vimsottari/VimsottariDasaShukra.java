/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.vimsottari.IVimsottariDasaShukra;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 9. Shukra vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaShukra implements IVimsottariDasaShukra {
    VD9,
    SKVD;

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public IVimsottariDasaShukra[] all() {
        return values();
    }
}
