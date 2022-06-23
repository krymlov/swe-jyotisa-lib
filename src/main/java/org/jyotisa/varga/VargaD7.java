/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD7;

import static org.jyotisa.api.rasi.IRasi.*;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.fix360;


/**
 * 7 Saptamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD7 implements IVargaD7 {
    D7;

    @Override
    public IVargaD7[] all() {
        return values();
    }

    @Override
    public double virtualDegree(double longitude) {
        final double result;
        double basepos = rasiFid0(longitude) * i30 + rasiDegree(longitude) * d7;
        if (inOddRasi(longitude)) result = basepos;
        else result = basepos + d180;
        return fix360(result);
    }
}
