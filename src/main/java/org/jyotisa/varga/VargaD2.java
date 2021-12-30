/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD2;
import org.jyotisa.rasi.ERasi;

import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * 2 Hora
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD2 implements IVargaD2 {
    D2;

    @Override
    public IVargaD2[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(final double longitude) {
        return modulo(d60, longitude - d15) + d90;
    }
}
