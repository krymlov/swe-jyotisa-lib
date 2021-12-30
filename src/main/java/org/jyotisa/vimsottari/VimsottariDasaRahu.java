/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaRahu;
import org.jyotisa.api.vimsottari.IVimsottariDasaRahu;

import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;

/**
 * 4. Rahu vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaRahu implements IVimsottariDasaRahu {
    VD4,
    RAVD;

    @Override
    public IGrahaRahu lord() {
        return RAHU;
    }

    @Override
    public IVimsottariDasaRahu[] all() {
        return values();
    }
}
