/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD60;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.swisseph.api.ISweConstants.d60;
import static org.swisseph.api.ISweConstants.i30;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 60 Shashtyamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD60 implements IVargaD60 {
    D60;

    @Override
    public IVargaD60[] all() {
        return values();
    }

    @Override
    public double virtualDegree(double longitude) {
        return fix360(d60 * rasiDegree(longitude) + rasiFid0(longitude) * i30);
    }
}
