/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.naksatra.INaksatraKrittika;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * 3. Krittika
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraKrittika implements INaksatraKrittika {
    N3,
    KRI;

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public INaksatraKrittika[] all() {
        return values();
    }
}
