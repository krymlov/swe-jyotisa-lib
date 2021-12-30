/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.vaara.IVaaraShukra;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;

/**
 * 6. Shukravaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraShukra implements IVaaraShukra {
    VR6,
    SKVR;

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public IVaaraShukra[] all() {
        return values();
    }
}
