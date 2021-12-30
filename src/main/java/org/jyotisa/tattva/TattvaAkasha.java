/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.tattva;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.tattva.ITattvaAkasha;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 1. AKASHA
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TattvaAkasha implements ITattvaAkasha {
    TT1,
    AKA;

    @Override
    public ITattvaAkasha[] all() {
        return values();
    }

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }
}
