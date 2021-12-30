/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaRahu;
import org.jyotisa.api.naksatra.INaksatraSwati;

import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;

/**
 * 15. Swati
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraSwati implements INaksatraSwati {
    N15,
    SWA;

    @Override
    public IGrahaRahu lord() {
        return RAHU;
    }

    @Override
    public INaksatraSwati[] all() {
        return values();
    }
}
