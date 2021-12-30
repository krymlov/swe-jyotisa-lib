/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-11
 */

package org.jyotisa.bindu;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.graha.IGrahas;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.rasi.ERasi;

import static org.jyotisa.bhava.EBhava.byUid;
import static org.jyotisa.naksatra.ENaksatraPada.byLongitude;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IDegreeUtils.toDMSms;

/**
 * It is the midpoint between the Rāhu and the Moon
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
public class BhriguBindu {
    protected final double longitude;
    protected final IBhava bhava;

    public BhriguBindu(IKundali kundali) {
        final IGrahas grahas = kundali.grahas();
        longitude = (grahas.chandra().longitude() + grahas.rahu().longitude()) / d2;
        bhava = byUid((ERasi.byLongitude(this.longitude).fid() + i12 - grahas.lagna().pada().rasi().fid()) % i12 + i1);
    }

    public INaksatraPada pada() {
        return byLongitude(longitude);
    }

    public double longitude() {
        return longitude;
    }

    public IBhava bhava() {
        return bhava;
    }

    @Override
    public String toString() {
        return toDMSms(longitude) + ", Bhava: " + bhava
            + ", Rasi: " + pada().rasi().following()
            + ", Naksatra: " + pada().naksatra().following();
    }
}
