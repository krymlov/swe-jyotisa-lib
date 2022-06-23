/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD45;

import static org.jyotisa.api.rasi.IRasi.*;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 45 Akshavedamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD45 implements IVargaD45 {
    D45;

    @Override
    public IVargaD45[] all() {
        return values();
    }

    @Override
    public double virtualDegree(double longitude) {
        final double result;
        double basepos = rasiDegree(longitude) * d45;
        if (inMovableRasi(longitude)) result = basepos;
        else if (inFixedRasi(longitude)) result = basepos + d120;
        else result = basepos + d240;
        return fix360(result);
    }
}
