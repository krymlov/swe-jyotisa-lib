/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaGuru;
import org.jyotisa.api.naksatra.INaksatraVishakha;

import static org.jyotisa.graha.guru.GrahaGuru.GURU;

/**
 * 16. Visakha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraVishakha implements INaksatraVishakha {
    N16,
    VIS;

    @Override
    public IGrahaGuru lord() {
        return GURU;
    }

    @Override
    public INaksatraVishakha[] all() {
        return values();
    }
}
