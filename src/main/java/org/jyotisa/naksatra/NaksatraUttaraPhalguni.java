/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaSurya;
import org.jyotisa.api.naksatra.INaksatraUttaraPhalguni;

import static org.jyotisa.graha.surya.GrahaSurya.SURYA;

/**
 * 12. Uttara Phalguni
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraUttaraPhalguni implements INaksatraUttaraPhalguni {
    N12,
    UPH;

    @Override
    public IGrahaSurya lord() {
        return SURYA;
    }

    @Override
    public INaksatraUttaraPhalguni[] all() {
        return values();
    }
}
