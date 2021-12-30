/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiMesha;
import org.jyotisa.api.tattva.ITattvaAgni;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;
import static org.jyotisa.tattva.TattvaAgni.AGN;
import static org.swisseph.app.SweGender.MALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiMesha implements IRasiMesha {
    R1,
    MES;

    @Override
    public IRasiMesha[] all() {
        return values();
    }

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public ISweGender gender() {
        return MALE;
    }

    @Override
    public ITattvaAgni tattva() {
        return AGN;
    }

    @Override
    public IRasi badhaka() {
        return ERasi.KUMBHA.rasi();
    }
}
