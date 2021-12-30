/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.tattva;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.tattva.ITattvaVayu;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 4. VAYU
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TattvaVayu implements ITattvaVayu {
    TT4,
    VAY;

    @Override
    public ITattvaVayu[] all() {
        return values();
    }

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }
}
