/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.surya;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaSurya;
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
 * 1. Surya
 *
 * @author Yura Krymlov
 * @version 1.0, 2019-10
 */
public enum GrahaSurya implements IGrahaSurya {
    G1,
    SY,
    SURYA;

    @Override
    public IGrahaSurya[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d0 && d <= d10) return UCC; // ARI 0-10
        if (d > d10 && d < d30) return EXT;  // ARI 10-30

        if (d >= d120 && d <= d140) return MLT;  // LEO 0-20
        if (d > d140 && d < d150) return SWT;   // LEO 20-30
        if (d >= d90 && d < d120 || d >= d210 && d < d270 || d >= d330 && d < d360) return MIR;
        if (d >= d60 && d < d90 || d >= d150 && d < d180) return SAM;
        if (d >= d30 && d < d60 || d >= d270 && d < d330) return SRU;

        if (d > d190 && d < d210) return DET;    // LIB 10-30
        if (d >= d180 && d <= d190) return NEE;  // LIB 0-10

        return null;
    }

}
