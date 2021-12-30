/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD81;
import org.jyotisa.rasi.ERasi;

import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * 81 NavaNavāṁśa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD81 implements IVargaD81 {
    D81;

    @Override
    public IVargaD81[] all() {
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
