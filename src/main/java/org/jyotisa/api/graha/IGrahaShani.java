/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.SA;
import static swisseph.SweConst.SE_SATURN;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaShani extends IGraha {
    double[] MRITYU_BHAGA_SHANI = new double[]{10, 4, 7, 9, 12, 16, 3, 18, 28, 14, 13, 15};

    /**
     * 8. Shani
     */
    @Override
    default int fid() {
        return 8;
    }

    /**
     * 7. Shani - Saturn
     */
    @Override
    default int uid() {
        return SA;
    }

    @Override
    default String code() {
        return SA_CD;
    }

    @Override
    default int swefid() {
        return SE_SATURN;
    }

    @Override
    default int[] drishti() {
        return DRISHTI_3710;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_SHANI);
    }
}
