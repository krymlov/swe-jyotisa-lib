/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaShukra;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiTula;
import org.jyotisa.api.tattva.ITattvaVayu;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.shukra.GrahaShukra.SHUKRA;
import static org.jyotisa.tattva.TattvaVayu.VAY;
import static org.swisseph.app.SweGender.MALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiTula implements IRasiTula {
    R7,
    TUL;

    @Override
    public IRasiTula[] all() {
        return values();
    }

    @Override
    public IGrahaShukra lord() {
        return SHUKRA;
    }

    @Override
    public ITattvaVayu tattva() {
        return VAY;
    }

    @Override
    public ISweGender gender() {
        return MALE;
    }

    @Override
    public IRasi badhaka() {
        return ERasi.SIMHA.rasi();
    }
}
