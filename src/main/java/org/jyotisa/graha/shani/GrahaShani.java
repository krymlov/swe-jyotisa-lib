/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.shani;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaShani;
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
public enum GrahaShani implements IGrahaShani {
    G8,
    SA,
    SHANI;

    @Override
    public IGrahaShani[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = IModuloUtils.fix360(longitude * varga.fid());

        if (d >= d180 && d <= d200) return UCC;
        if (d > d200 && d < d210) return EXT;

        if (d >= d300 && d <= d320) return MLT;
        if (d >= d270 && d < d300 || d > d320 && d < d330) return SWT;

        if (d >= d30 && d < d90 || d >= d150 && d < d180) return MIR;
        if (d >= d240 && d < d270 || d >= d330 && d < d360) return SAM;
        if (d >= d90 && d < d150 || d >= d210 && d < d240) return SRU;

        if (d > d20 && d < d30) return DET;
        if (d >= d0 && d <= d20) return NEE;

        return null;
    }

}
