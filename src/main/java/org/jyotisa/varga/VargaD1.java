/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD1;
import org.jyotisa.rasi.ERasi;

import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 1 Rasi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD1 implements IVargaD1 {
    D1;

    @Override
    public IVargaD1[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(longitude);
    }

    public double vargaLongitude(final double longitude) {
        return fix360(longitude * fid());
    }

}
