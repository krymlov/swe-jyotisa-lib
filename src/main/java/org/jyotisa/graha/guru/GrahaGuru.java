/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.guru;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaGuru;
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
public enum GrahaGuru implements IGrahaGuru {
    G3, GU, GURU, Ju, Jupiter;

    @Override
    public IGrahaGuru[] all() {
        return values();
    }

    @Override
    public IDignity dignity(final IVarga varga, final double longitude) {
        final double d = varga.chakraLongitude(longitude);

        if (d >= d90 && d <= d95) return UCC;
        if (d > d95 && d < d120) return EXT;

        if (d >= d240 && d <= d250) return MLT;
        if (d > d250 && d < d270 || d >= d330 && d < d360) return SWT;

        if (d >= d0 && d < d30 || d >= d120 && d < d150 || d >= d210 && d < d240) return MIR;
        if (d >= d300 && d < d330) return SAM;

        if (d >= d30 && d < d90 || d >= d150 && d < d210) return SRU;

        if (d > d275 && d < d300) return DET;
        if (d >= d270 && d <= d275) return NEE;

        return null;
    }
}
