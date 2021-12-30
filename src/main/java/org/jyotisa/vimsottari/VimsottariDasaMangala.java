/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vimsottari;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.vimsottari.IVimsottariDasaMangala;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VimsottariDasaMangala implements IVimsottariDasaMangala {
    VD3,
    MAVD;

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public IVimsottariDasaMangala[] all() {
        return values();
    }
}
