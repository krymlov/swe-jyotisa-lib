/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.naksatra.INaksatraHasta;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * 13. Hasta
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraHasta implements INaksatraHasta {
    N13,
    HAS;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public INaksatraHasta[] all() {
        return values();
    }
}
