/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiKanya;
import org.jyotisa.api.tattva.ITattvaPrithvi;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;
import static org.jyotisa.tattva.TattvaPrithvi.PRI;
import static org.swisseph.app.SweGender.FEMALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiKanya implements IRasiKanya {
    R6,
    KAN;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
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
    public IRasiKanya[] all() {
        return values();
    }

    @Override
    public IRasi badhaka() {
        return ERasi.MEENA.rasi();
    }
}
