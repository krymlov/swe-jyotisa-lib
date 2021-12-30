/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.chaya;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaRahu;
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
public enum GrahaRahu implements IGrahaRahu {
    G4,
    RA,
    RAHU,
    RAHU_TRUE {
        @Override
        public int swefid() {
            return SE_TRUE_NODE;
        }
    };

    @Override
    public IGrahaRahu[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d60 && d < d90) return UCC;
        if (d >= d150 && d < d180) return MLT;
        if (d >= d300 && d < d330) return SWT;
        if (d >= d240 && d < d270) return NEE;

        return null;
    }
}
