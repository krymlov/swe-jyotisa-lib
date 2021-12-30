/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaRahu;
import org.jyotisa.api.naksatra.INaksatraShatabhisha;

import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;

/**
 * 24. Shatabisha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraShatabhisha implements INaksatraShatabhisha {
    N24,
    SAT;

    @Override
    public IGrahaRahu lord() {
        return RAHU;
    }

    @Override
    public INaksatraShatabhisha[] all() {
        return values();
    }
}
