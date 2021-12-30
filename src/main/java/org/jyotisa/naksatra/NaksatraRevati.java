/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.naksatra.INaksatraRevati;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 27. Revati
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraRevati implements INaksatraRevati {
    N27,
    REV;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public INaksatraRevati[] all() {
        return values();
    }
}
