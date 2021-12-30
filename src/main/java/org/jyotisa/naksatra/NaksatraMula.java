/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaKetu;
import org.jyotisa.api.naksatra.INaksatraMula;

import static org.jyotisa.graha.chaya.GrahaKetu.KETU;

/**
 * 19. Moola
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraMula implements INaksatraMula {
    N19,
    MUL;

    @Override
    public IGrahaKetu lord() {
        return KETU;
    }

    @Override
    public INaksatraMula[] all() {
        return values();
    }
}
