/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.vaara.IVaaraSurya;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraSurya implements IVaaraSurya {
    VR1,
    SYVR;

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public IVaaraSurya[] all() {
        return values();
    }
}
