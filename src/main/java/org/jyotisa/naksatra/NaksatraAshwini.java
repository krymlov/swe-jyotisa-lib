/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaKetu;
import org.jyotisa.api.naksatra.INaksatraAshwini;

import static org.jyotisa.graha.chaya.GrahaKetu.KETU;

/**
 * 1. Ashwini
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraAshwini implements INaksatraAshwini {
    N1,
    ASH;

    @Override
    public IGrahaKetu lord() {
        return KETU;
    }

    @Override
    public INaksatraAshwini[] all() {
        return values();
    }
}
