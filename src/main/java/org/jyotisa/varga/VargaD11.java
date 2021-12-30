/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD11;
import org.jyotisa.rasi.ERasi;

import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 11 Rudramsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD11 implements IVargaD11 {
    D11;

    @Override
    public IVargaD11[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(final double longitude) {
        return fix360(longitude * fid());
    }

}
