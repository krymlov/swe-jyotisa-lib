/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.shukra;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaShukra;
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
public enum GrahaShukra implements IGrahaShukra {
    G6,
    SK,
    SHUKRA;

    @Override
    public IGrahaShukra[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d330 && d <= d357) return UCC;
        if (d > d357 && d < d360) return EXT;

        if (d >= d180 && d <= d195) return MLT;
        if (d >= d30 && d < d60 || d > d195 && d < d210) return SWT;

        if (d >= d60 && d < d90 || d >= d270 && d < d330) return MIR;
        if (d >= d0 && d < d30 || d >= d210 && d < d270) return SAM;

        if (d >= d90 && d < d150) return SRU;

        if (d > d177 && d < d180) return DET;
        if (d >= d150 && d <= d177) return NEE;

        return null;
    }
}
