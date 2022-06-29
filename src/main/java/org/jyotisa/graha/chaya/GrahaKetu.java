/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.chaya;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaKetu;
import org.jyotisa.api.varga.IVarga;
import org.swisseph.utils.IModuloUtils;

import static org.jyotisa.dignity.DignityMulatrikona.MLT;
import static org.jyotisa.dignity.DignityNeecha.NEE;
import static org.jyotisa.dignity.DignitySwakshetra.SWT;
import static org.jyotisa.dignity.DignityUccha.UCC;
import static org.swisseph.api.ISweConstants.*;
import static swisseph.SweConst.SE_TRUE_NODE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-10
 */
public enum GrahaKetu implements IGrahaKetu {
    G7,
    KE, KETU,
    Ke, Ketu,
    KETU_TRUE {
        @Override
        public int swefid() {
            return SE_TRUE_NODE;
        }
    };

    @Override
    public IGrahaKetu[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d240 && d < d270) return UCC;
        if (d >= d330 && d < d360) return MLT;
        if (d >= d210 && d < d240) return SWT;
        if (d >= d60 && d < d90) return NEE;

        return null;
    }
}
