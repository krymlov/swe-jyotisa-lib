/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiVrischika;
import org.jyotisa.api.tattva.ITattvaJala;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;
import static org.jyotisa.tattva.TattvaJala.JAL;
import static org.swisseph.app.SweGender.FEMALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiVrischika implements IRasiVrischika {
    R8,
    VRC;

    @Override
    public IRasiVrischika[] all() {
        return values();
    }

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
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
        return ERasi.KARKATA.rasi();
    }
}
