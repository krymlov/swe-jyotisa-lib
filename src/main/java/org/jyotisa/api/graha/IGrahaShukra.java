/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import static org.swisseph.api.ISweObjects.SK;
import static swisseph.SweConst.SE_VENUS;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaShukra extends IGraha {
    double[] MRITYU_BHAGA_SHUKRA = new double[]{28, 15, 11, 17, 10, 13, 4, 6, 27, 12, 29, 19};

    /**
     * 6. Shukra
     */
    @Override
    default int fid() {
        return SK;
    }

    /**
     * 6. Shukra - Venus
     */
    @Override
    default int uid() {
        return SK;
    }

    @Override
    default String code() {
        return SK_CD;
    }

    @Override
    default int swefid() {
        return SE_VENUS;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_SHUKRA);
    }
}
