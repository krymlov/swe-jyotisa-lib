/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD4;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 4 Chaturthamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD4 implements IVargaD4 {
    D4;

    @Override
    public IVargaD4[] all() {
        return values();
    }

    @Override
    public double virtualDegree(double longitude) {
        longitude = (Math.floor(rasiDegree(longitude) / 7.5) * d90
                + rasiFid0(longitude) * i30 + rasiDegree(d4 * longitude));
        return fix360(longitude);
    }
}
