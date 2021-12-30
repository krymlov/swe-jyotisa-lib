/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaChandra;
import org.jyotisa.api.naksatra.INaksatraShravana;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;

/**
 * 22. Shravana
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraShravana implements INaksatraShravana {
    N22,
    SHR;

    @Override
    public IGrahaChandra lord() {
        return CHANDRA;
    }

    @Override
    public INaksatraShravana[] all() {
        return values();
    }
}
