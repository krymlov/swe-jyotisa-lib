package org.jyotisa.api.graha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.varga.IVarga;

import static org.swisseph.api.ISweObjects.LG;
import static swisseph.SweConst.ERR;

public interface IGrahaLagna extends IGraha {
    double[] MRITYU_BHAGA_LAGNA = new double[]{1, 9, 22, 22, 25, 2, 4, 23, 18, 20, 24, 10};

    /**
     * 0. Lagna
     */
    @Override
    default int fid() {
        return LG;
    }

    /**
     * 0. Lagna - Asc
     */
    @Override
    default int uid() {
        return LG;
    }

    @Override
    default String code() {
        return LG_CD;
    }

    @Override
    default int swefid() {
        return ERR;
    }

    @Override
    default int[] drishti() {
        return NO_DRISHTI;
    }

    @Override
    default IDignity dignity(final IVarga varga, final double longitude) {
        return null;
    }

    @Override
    default double inMrityuBhaga(final double longitude) {
        return IGraha.inMrityuBhaga(longitude, MRITYU_BHAGA_LAGNA);
    }
}
