/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.SY;
import static swisseph.SweConst.SE_SUN;

/**
 * 1. Surya
 *
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaSurya extends IGraha {
    double[] MRITYU_BHAGA_SURYA = new double[]{20, 9, 12, 6, 8, 24, 16, 17, 22, 2, 3, 23};

    /**
     * 1. Surya
     */
    @Override
    default int fid() {
        return SY;
    }

    /**
     * 1. Surya - Sun
     */
    @Override
    default int uid() {
        return SY;
    }

    @Override
    default String code() {
        return SY_CD;
    }

    @Override
    default int swefid() {
        return SE_SUN;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_SURYA);
    }
}
