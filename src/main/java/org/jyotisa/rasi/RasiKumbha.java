/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiKumbha;
import org.jyotisa.api.tattva.ITattvaVayu;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;
import static org.jyotisa.tattva.TattvaVayu.VAY;
import static org.swisseph.app.SweGender.MALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiKumbha implements IRasiKumbha {
    R11,
    KUM;

    @Override
    public IRasiKumbha[] all() {
        return values();
    }

    @Override
    public IGrahaShani lord() {
        return SHANI;
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
        return ERasi.TULA.rasi();
    }
}
