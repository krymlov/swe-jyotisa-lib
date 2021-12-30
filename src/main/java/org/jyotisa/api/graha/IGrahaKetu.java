/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.KE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaKetu extends IGrahaChaya {
    double[] MRITYU_BHAGA_KETU = new double[]{8, 18, 20, 10, 21, 22, 23, 24, 11, 12, 13, 14};

    /**
     * 7. Ketu
     */
    @Override
    default int fid() {
        return 7;
    }

    /**
     * 9. Ketu - South node
     */
    @Override
    default int uid() {
        return KE;
    }

    @Override
    default String code() {
        return KE_CD;
    }

    @Override
    default int[] drishti() {
        return NO_DRISHTI;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_KETU);
    }
}
