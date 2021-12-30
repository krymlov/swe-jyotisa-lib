/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.chandra;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.varga.IVarga;

import static org.jyotisa.dignity.DignityDeficient.DET;
import static org.jyotisa.dignity.DignityMitra.MIR;
import static org.jyotisa.dignity.DignityMulatrikona.MLT;
import static org.jyotisa.dignity.DignityNeecha.NEE;
import static org.jyotisa.dignity.DignitySama.SAM;
import static org.jyotisa.dignity.DignitySwakshetra.SWT;
import static org.jyotisa.dignity.DignityUccha.UCC;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-10
 */
public enum GrahaChandra implements IGrahaChandra {
    G2,
    CH,
    CHANDRA;

    @Override
    public IGrahaChandra[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = fix360(longitude * varga.fid());

        if (d >= d30 && d < d34) return UCC;
        if (d >= d34 && d <= d60) return MLT;
        if (d >= d90 && d < d120) return SWT;

        if (d >= d60 && d < d90 || d >= d120 && d < d180) return MIR;
        if (d >= d0 && d < d30 || d >= d180 && d < d210 || d >= d240 && d < d360) return SAM;

        if (d > d213 && d < d240) return DET;
        if (d >= d210 && d <= d213) return NEE;

        return null;
    }

}
