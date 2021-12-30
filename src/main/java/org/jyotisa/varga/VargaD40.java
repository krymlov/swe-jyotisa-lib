/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD40;
import org.jyotisa.rasi.ERasi;

import static org.jyotisa.api.rasi.IRasi.inOddRasi;
import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.swisseph.api.ISweConstants.d180;
import static org.swisseph.api.ISweConstants.d40;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 40 Khavedamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD40 implements IVargaD40 {
    D40;

    @Override
    public IVargaD40[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(double longitude) {
        final double result;
        double basepos = rasiDegree(longitude) * d40;
        if (inOddRasi(longitude)) result = basepos;
        else result = basepos + d180;
        return fix360(result);
    }

}
