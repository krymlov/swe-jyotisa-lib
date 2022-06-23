/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD2;

import static org.jyotisa.rasi.ERasi.byLongitude;
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
    public IRasi rasi(final double longitudeInD1) {
        return byLongitude(modulo(d60, longitudeInD1 - d15) + d90);
    }
}
