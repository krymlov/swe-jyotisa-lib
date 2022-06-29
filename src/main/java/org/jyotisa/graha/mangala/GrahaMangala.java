/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.mangala;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.varga.IVarga;
import org.swisseph.utils.IModuloUtils;

import static org.jyotisa.dignity.DignityDeficient.DET;
import static org.jyotisa.dignity.DignityExcellent.EXT;
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
public enum GrahaMangala implements IGrahaMangala {
    G9,
    MA, MANGALA,
    Ma, Mars;

    @Override
    public IGrahaMangala[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d270 && d <= d298) return UCC;
        if (d > d298 && d < d300) return EXT;

        if (d >= d0 && d <= d12) return MLT;
        if (d > d12 && d < d30 || d >= d210 && d < d240) return SWT;

        if (d >= d120 && d < d150 || d >= d240 && d < d270 || d >= d330 && d < d360) return MIR;
        if (d >= d30 && d < d60 || d >= d180 && d < d210 || d >= d300 && d < d330) return SAM;

        if (d >= d60 && d < d90 || d >= d150 && d < d180) return SRU;

        if (d > d118 && d < d120) return DET;
        if (d >= d90 && d <= d118) return NEE;

        return null;
    }
}
