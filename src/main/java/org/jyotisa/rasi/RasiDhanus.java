/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiDhanus;
import org.jyotisa.api.tattva.ITattvaAgni;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;
import static org.jyotisa.tattva.TattvaAgni.AGN;
import static org.swisseph.app.SweGender.MALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiDhanus implements IRasiDhanus {
    R9,
    DHN;

    @Override
    public IRasiDhanus[] all() {
        return values();
    }

    @Override
    public IGrahaGuru lord() {
        return GURU;
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
        return ERasi.MITHUNA.rasi();
    }
}
