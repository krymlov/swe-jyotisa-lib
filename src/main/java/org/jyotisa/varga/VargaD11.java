/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD11;

import static org.jyotisa.rasi.ERasi.byLongitude;

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
    public IRasi rasi(final double longitudeInD1) {
        return byLongitude(virtualDegree(longitudeInD1));
    }
}
