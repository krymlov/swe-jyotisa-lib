/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.rasi;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiMeena;
import org.jyotisa.api.tattva.ITattvaJala;
import org.swisseph.api.ISweGender;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;
import static org.jyotisa.tattva.TattvaJala.JAL;
import static org.swisseph.app.SweGender.FEMALE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum RasiMeena implements IRasiMeena {
    R12,
    MEE;

    @Override
    public IRasiMeena[] all() {
        return values();
    }

    @Override
    public IGrahaGuru lord() {
        return GURU;
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
        return ERasi.KANYA.rasi();
    }
}
