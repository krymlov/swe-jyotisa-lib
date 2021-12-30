/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.vaara.IVaaraShani;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 7. Shanivaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraShani implements IVaaraShani {
    VR7,
    SAVR;

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public IVaaraShani[] all() {
        return values();
    }
}
