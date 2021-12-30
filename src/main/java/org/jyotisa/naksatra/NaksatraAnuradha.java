/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaShani;
import org.jyotisa.api.naksatra.INaksatraAnuradha;

import static org.jyotisa.graha.shani.GrahaShani.SHANI;

/**
 * 17. Anuradha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraAnuradha implements INaksatraAnuradha {
    N17,
    ANU;

    @Override
    public IGrahaShani lord() {
        return SHANI;
    }

    @Override
    public INaksatraAnuradha[] all() {
        return values();
    }
}
