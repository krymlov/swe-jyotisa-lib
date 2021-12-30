/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.varga.IVarga;

import static org.swisseph.api.ISweConstants.d0;
import static org.swisseph.api.ISweObjects.UR;
import static swisseph.SweConst.SE_URANUS;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaSweta extends IGraha {

    /**
     * 10. Uranus
     */
    @Override
    default int fid() {
        return UR;
    }

    /**
     * 10. Uranus
     */
    @Override
    default int uid() {
        return UR;
    }

    @Override
    default String code() {
        return UR_CD;
    }

    @Override
    default int swefid() {
        return SE_URANUS;
    }

    @Override
    default IDignity dignity(final IVarga varga, final double longitude) {
        return null;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return d0;
    }
}
