/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiMithuna;
import org.jyotisa.api.tattva.ITattvaVayu;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;
import static org.jyotisa.tattva.TattvaVayu.VAY;
import static org.swisseph.app.SweGender.MALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiMithuna implements IRasiMithuna {
    R3,
    MIT;

    @Override
    public IRasiMithuna[] all() {
        return values();
    }

    @Override
    public ISweGender gender() {
        return MALE;
    }

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public ITattvaVayu tattva() {
        return VAY;
    }

    @Override
    public IRasi badhaka() {
        return ERasi.DHANUS.rasi();
    }
}
