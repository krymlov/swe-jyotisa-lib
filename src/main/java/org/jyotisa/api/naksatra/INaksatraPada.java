/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.api.naksatra;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.rasi.IRasi;
import org.swisseph.api.ISweSegment;
import org.swisseph.app.SweSegment;

import static org.swisseph.api.ISweConstants.D100_NAKSHATRA_PADA_LENGTH;
import static org.swisseph.api.ISweConstants.NAKSHATRA_PADA_LENGTH;
import static org.swisseph.app.SweSegment.ZERO_SEGMENT;
import static org.swisseph.utils.IDegreeUtils.round;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface INaksatraPada extends IKundaliSequence<INaksatraPada> {
    IRasi rasi();
    IRasi navamsa();
    INaksatra naksatra();

    @Override
    default int fid() {
        final INaksatra naksatra = naksatra();
        if (null == naksatra) return NIL_FID;
        return Integer.parseInt(String.valueOf(naksatra.fid()) + pada());
    }

    @Override
    default String code() {
        return naksatra().code() + "P" + pada();
    }

    default int pada() {
        return 1 + (uid() - 1) % 4;
    }

    default double length() {
        return NAKSHATRA_PADA_LENGTH;
    }

    @Override
    default ISweSegment segment() {
        if (0 == uid()) return ZERO_SEGMENT;

        final int idx = uid();
        final double length = length();
        return new SweSegment((idx - 1) * length, idx * length);
    }

    static double progress(final IPanchanga panchanga) {
        return round(modulo(NAKSHATRA_PADA_LENGTH,
            panchanga.chandraLongitude()) * D100_NAKSHATRA_PADA_LENGTH, 2);
    }

    static double progress(final double longitude) {
        return round(modulo(NAKSHATRA_PADA_LENGTH, longitude)
            * D100_NAKSHATRA_PADA_LENGTH, 2);
    }

}
