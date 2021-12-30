/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaKetu;
import org.jyotisa.api.naksatra.INaksatraMagha;

import static org.jyotisa.graha.chaya.GrahaKetu.KETU;

/**
 * 10. Magha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraMagha implements INaksatraMagha {
    N10,
    MAG;

    @Override
    public IGrahaKetu lord() {
        return KETU;
    }

    @Override
    public INaksatraMagha[] all() {
        return values();
    }
}
