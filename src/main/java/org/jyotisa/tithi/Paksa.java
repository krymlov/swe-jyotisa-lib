/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-09
*/

package org.jyotisa.tithi;


import org.jyotisa.api.tithi.IPaksa;

import static org.swisseph.api.ISweConstants.*;

/**
 * <pre>
 * Paksha (or pakṣa: Sanskrit: पक्ष) refers to a fortnight or a lunar phase in a month.<br>
 * The first fortnight between New Moon Day and Full Moon Day is called "Gaura Paksha" or Shukla Paksha, the
 * period of the brightening moon (waxing moon), and the second fortnight of the month is called Krishna Paksha
 * 
 * Range 0..1, or {k, K, krsna, Gaura, g, G, gaura}
 * <pre>
 * 
 * @author Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum Paksa implements IPaksa {
    KRSNA, // 0 for Krsna Paksa
    GAURA; // 1 for Gaura Paksa

    @Override
    public int fid() {
        return ordinal();
    }
    
    @Override
    public int uid() {
        return ordinal();
    }
    
    @Override
    public String code() {
        return name();
    }

    @Override
    public boolean isKrsna() {
        return this == KRSNA;
    }

    @Override
    public boolean isGaura() {
        return this == GAURA;
    }

    public static IPaksa byLongitude(final double surya, final double chandra) {
        double diff = chandra - surya;
        if ( d0 > diff ) diff += d360;

        int tithiIdx = 1 + (int)(diff / d12);
        if ( tithiIdx > 15 ) return KRSNA;
        else return GAURA;
    }
}
