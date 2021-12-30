/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.vaara.IVaaraChandra;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * 2. Chandravaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraChandra implements IVaaraChandra {
    VR2,
    CHVR;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public IVaaraChandra[] all() {
        return values();
    }
}
