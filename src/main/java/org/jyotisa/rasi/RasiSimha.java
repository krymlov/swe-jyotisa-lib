/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiSimha;
import org.jyotisa.api.tattva.ITattvaAgni;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;
import static org.jyotisa.tattva.TattvaAgni.AGN;
import static org.swisseph.app.SweGender.MALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiSimha implements IRasiSimha {
    R5,
    SIM;

    @Override
    public IRasiSimha[] all() {
        return values();
    }

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public ITattvaAgni tattva() {
        return AGN;
    }

    @Override
    public ISweGender gender() {
        return MALE;
    }

    @Override
    public IRasi badhaka() {
        return ERasi.MESHA.rasi();
    }
}
