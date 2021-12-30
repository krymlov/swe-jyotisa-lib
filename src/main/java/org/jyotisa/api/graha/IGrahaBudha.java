/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.BU;
import static swisseph.SweConst.SE_MERCURY;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaBudha extends IGraha {
    double[] MRITYU_BHAGA_BUDHA = new double[]{15, 14, 13, 12, 8, 18, 20, 10, 21, 22, 7, 5};

    /**
     * 5. Budha
     */
    @Override
    default int fid() {
        return 5;
    }

    /**
     * 4. Budha - Mercury
     */
    @Override
    default int uid() {
        return BU;
    }

    @Override
    default String code() {
        return BU_CD;
    }

    @Override
    default int swefid() {
        return SE_MERCURY;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_BUDHA);
    }
}
