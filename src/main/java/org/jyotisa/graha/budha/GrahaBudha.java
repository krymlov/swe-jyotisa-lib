/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.budha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.varga.IVarga;
import org.swisseph.utils.IModuloUtils;

import static org.jyotisa.dignity.DignityDeficient.DET;
import static org.jyotisa.dignity.DignityMitra.MIR;
import static org.jyotisa.dignity.DignityMulatrikona.MLT;
import static org.jyotisa.dignity.DignityNeecha.NEE;
import static org.jyotisa.dignity.DignitySama.SAM;
import static org.jyotisa.dignity.DignitySatru.SRU;
import static org.jyotisa.dignity.DignitySwakshetra.SWT;
import static org.jyotisa.dignity.DignityUccha.UCC;
import static org.swisseph.api.ISweConstants.*;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-10
 */
public enum GrahaBudha implements IGrahaBudha {
    G5,
    BU, BUDHA,
    Me, Mercury;

    @Override
    public IGrahaBudha[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d150 && d < d165) return UCC;
        if (d >= d165 && d <= d170) return MLT;
        if (d > d170 && d < d180 || d >= d60 && d < d90) return SWT;

        if (d >= d30 && d < d60 || d >= d120 && d < d150 || d >= d180 && d < d210) return MIR;
        if (d >= d0 && d < d30 || d >= d210 && d < d330) return SAM;
        if (d >= d90 && d < d120) return SRU;

        if (d > d345 && d < d360) return DET;
        if (d >= d330 && d <= d345) return NEE;

        return null;
    }
}
