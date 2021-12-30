/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaKetu;
import org.jyotisa.api.vimsottari.IVimsottariDasaKetu;

import static org.jyotisa.graha.chaya.GrahaKetu.KETU;

/**
 * 8. Ketu vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaKetu implements IVimsottariDasaKetu {
    VD8,
    KEVD;

    @Override
    public IGrahaKetu lord() {
        return KETU;
    }

    @Override
    public IVimsottariDasaKetu[] all() {
        return values();
    }
}
