/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiMakara;
import org.jyotisa.api.tattva.ITattvaPrithvi;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;
import static org.jyotisa.tattva.TattvaPrithvi.PRI;
import static org.swisseph.app.SweGender.FEMALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiMakara implements IRasiMakara {
    R10,
    MAK;

    @Override
    public IRasiMakara[] all() {
        return values();
    }

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public ISweGender gender() {
        return FEMALE;
    }

    @Override
    public ITattvaPrithvi tattva() {
        return PRI;
    }

    @Override
    public IRasi badhaka() {
        return ERasi.VRISCHIKA.rasi();
    }
}
