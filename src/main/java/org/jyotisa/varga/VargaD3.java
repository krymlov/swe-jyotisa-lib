/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD3;
import org.jyotisa.rasi.ERasi;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
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
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(double longitude) {
        longitude = (Math.floor(rasiDegree(longitude) / d10) * d120
            + rasiFid0(longitude) * i30 + rasiDegree(d3 * longitude));
        return fix360(longitude);
    }

}
