/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiKarkata;
import org.jyotisa.api.tattva.ITattvaJala;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.jyotisa.tattva.TattvaJala.JAL;
import static org.swisseph.app.SweGender.FEMALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiKarkata implements IRasiKarkata {
    R4,
    KAR;

    @Override
    public IRasiKarkata[] all() {
        return values();
    }

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public ISweGender gender() {
        return FEMALE;
    }

    @Override
    public ITattvaJala tattva() {
        return JAL;
    }

    @Override
    public IRasi badhaka() {
        return ERasi.VRISHABHA.rasi();
    }
}
