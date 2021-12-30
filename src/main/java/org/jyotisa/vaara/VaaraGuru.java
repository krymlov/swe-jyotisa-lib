/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.vaara.IVaaraGuru;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 5. Guruvaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraGuru implements IVaaraGuru {
    VR5,
    GUVR;

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public IVaaraGuru[] all() {
        return values();
    }
}
