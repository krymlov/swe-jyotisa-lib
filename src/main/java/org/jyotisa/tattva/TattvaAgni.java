/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.tattva;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.tattva.ITattvaAgni;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 2. AGNI
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TattvaAgni implements ITattvaAgni {
    TT2,
    AGN;

    @Override
    public ITattvaAgni[] all() {
        return values();
    }

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }
}
