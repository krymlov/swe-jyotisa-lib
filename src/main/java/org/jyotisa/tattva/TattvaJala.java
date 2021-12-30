/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.tattva;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.tattva.ITattvaJala;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 5. JALA
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TattvaJala implements ITattvaJala {
    TT5,
    JAL;

    @Override
    public ITattvaJala[] all() {
        return values();
    }

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }
}
