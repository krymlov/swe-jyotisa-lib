/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD3;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.jyotisa.rasi.ERasi.byLongitude;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 3 Drekkana
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD3 implements IVargaD3 {
    D3;

    @Override
    public IVargaD3[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitudeInD1) {
        return byLongitude(virtualDegree(longitudeInD1));
    }

    @Override
    public double virtualDegree(double longitude) {
        longitude = (Math.floor(rasiDegree(longitude) / d10) * d120
                + rasiFid0(longitude) * i30 + rasiDegree(d3 * longitude));
        return fix360(longitude);
    }
}
