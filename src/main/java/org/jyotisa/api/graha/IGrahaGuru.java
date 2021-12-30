/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.GU;
import static swisseph.SweConst.SE_JUPITER;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaGuru extends IGraha {
    double[] MRITYU_BHAGA_GURU = new double[]{19, 29, 12, 27, 6, 4, 13, 10, 17, 11, 15, 28};

    /**
     * 3. Guru
     */
    @Override
    default int fid() {
        return 3;
    }

    /**
     * 5. Guru - Jupiter
     */
    @Override
    default int uid() {
        return GU;
    }

    @Override
    default String code() {
        return GU_CD;
    }

    @Override
    default int swefid() {
        return SE_JUPITER;
    }

    @Override
    default int[] drishti() {
        return DRISHTI_579;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_GURU);
    }
}
