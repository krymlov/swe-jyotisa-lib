/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD24;
import org.jyotisa.rasi.ERasi;

import static org.jyotisa.api.rasi.IRasi.inOddRasi;
import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 24 Chaturvimsamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD24 implements IVargaD24 {
    D24;

    @Override
    public IVargaD24[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(double longitude) {
        final double result;
        double basepos = rasiDegree(longitude) * d24;
        if (inOddRasi(longitude)) result = basepos + d120;
        else result = basepos + d90;
        return fix360(result);
    }

}
