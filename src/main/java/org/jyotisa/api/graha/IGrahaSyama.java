/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.graha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.varga.IVarga;

import static org.swisseph.api.ISweConstants.d0;
import static org.swisseph.api.ISweObjects.NE;
import static swisseph.SweConst.SE_NEPTUNE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IGrahaSyama extends IGraha {

    /**
     * 11. Neptune
     */
    @Override
    default int fid() {
        return NE;
    }

    /**
     * 11. Neptune
     */
    @Override
    default int uid() {
        return NE;
    }

    @Override
    default String code() {
        return NE_CD;
    }

    @Override
    default int swefid() {
        return SE_NEPTUNE;
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
