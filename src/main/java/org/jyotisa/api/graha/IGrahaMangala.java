/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.MA;
import static swisseph.SweConst.SE_MARS;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaMangala extends IGraha {
    double[] MRITYU_BHAGA_MANGALA = new double[]{19, 28, 25, 23, 29, 28, 14, 21, 2, 15, 11, 6};

    /**
     * 9. Mangala
     */
    @Override
    default int fid() {
        return 9;
    }

    /**
     * 3. Mangala - Mars
     */
    @Override
    default int uid() {
        return MA;
    }

    @Override
    default String code() {
        return MA_CD;
    }

    @Override
    default int swefid() {
        return SE_MARS;
    }

    @Override
    default int[] drishti() {
        return DRISHTI_478;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_MANGALA);
    }
}
