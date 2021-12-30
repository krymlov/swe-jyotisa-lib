/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.vimsottari.IVimsottariDasaBudha;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 7. Budha vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaBudha implements IVimsottariDasaBudha {
    VD7,
    BUVD;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public IVimsottariDasaBudha[] all() {
        return values();
    }
}
