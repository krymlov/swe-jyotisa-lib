/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import org.swisseph.api.ISweObjects;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaRahu extends IGrahaChaya {
    double[] MRITYU_BHAGA_RAHU = new double[]{14, 13, 12, 11, 24, 23, 22, 21, 10, 20, 18, 8};

    /**
     * 4. Rahu
     */
    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return RA_CD;
    }

    /**
     * 8. Rahu - North node
     */
    @Override
    default int uid() {
        return ISweObjects.RA;
    }

    @Override
    default int[] drishti() {
        return DRISHTI_579;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_RAHU);
    }
}
