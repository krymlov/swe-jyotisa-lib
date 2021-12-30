/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.CH;
import static swisseph.SweConst.SE_MOON;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaChandra extends IGraha {
    double[] MRITYU_BHAGA_CHANDRA = new double[]{26, 12, 13, 25, 24, 11, 26, 14, 13, 25, 5, 12};

    /**
     * 2. Chandra
     */
    @Override
    default int fid() {
        return CH;
    }

    /**
     * 2. Chandra - Moon
     */
    @Override
    default int uid() {
        return CH;
    }

    @Override
    default String code() {
        return CH_CD;
    }

    @Override
    default int swefid() {
        return SE_MOON;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_CHANDRA);
    }
}
