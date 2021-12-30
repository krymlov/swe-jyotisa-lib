/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.naksatra.INaksatraUttaraAshadha;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * 21. Uttarashadha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraUttaraAshadha implements INaksatraUttaraAshadha {
    N21,
    USH;

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public INaksatraUttaraAshadha[] all() {
        return values();
    }
}
