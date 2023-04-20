/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaD108;

import static org.jyotisa.rasi.ERasi.byLongitude;
import static org.swisseph.api.ISweConstants.d9;

/**
 * 108 Ashtottaramsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD108 implements IVargaD108 {
    D108;

    @Override
    public IVargaD108[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitudeInD1) {
        return byLongitude(virtualDegree(longitudeInD1));
    }

    @Override
    public double virtualDegree(double longitude) {
        return IVarga.dvadasamsaLongitude(d9 * longitude);
    }
}
