/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.varga.IVarga;

import static org.swisseph.api.ISweConstants.d0;
import static org.swisseph.api.ISweObjects.PL;
import static swisseph.SweConst.SE_PLUTO;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaTeevra extends IGraha {

    /**
     * 12. Pluto
     */
    @Override
    default int fid() {
        return PL;
    }

    /**
     * 12. Pluto
     */
    @Override
    default int uid() {
        return PL;
    }

    @Override
    default String code() {
        return PL_CD;
    }

    @Override
    default int swefid() {
        return SE_PLUTO;
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
