/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.vimsottari.IVimsottariDasaSurya;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaSurya implements IVimsottariDasaSurya {
    VD1,
    SYVD;

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public IVimsottariDasaSurya[] all() {
        return values();
    }
}
