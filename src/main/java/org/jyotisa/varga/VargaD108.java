/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaD108;
import org.jyotisa.rasi.ERasi;

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
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(final double longitude) {
        return IVarga.getDvadasamsaLongitude(d9 * longitude);
    }

}
