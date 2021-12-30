/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaD144;
import org.jyotisa.rasi.ERasi;


/**
 * 144 Dvadasdvadasamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD144 implements IVargaD144 {
    D144;

    @Override
    public IVargaD144[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        return ERasi.byLongitude(vargaLongitude(longitude));
    }

    public double vargaLongitude(double longitude) {
        longitude = IVarga.getDvadasamsaLongitude(longitude);
        return IVarga.getDvadasamsaLongitude(longitude);
    }

}