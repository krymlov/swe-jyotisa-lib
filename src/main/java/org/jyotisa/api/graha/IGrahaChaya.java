/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.graha;

import org.swisseph.utils.IDegreeUtils;

import static org.swisseph.api.ISweConstants.d100;
import static swisseph.SweConst.SE_MEAN_NODE;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IGrahaChaya extends IGraha {

    @Override
    default int swefid() {
        return SE_MEAN_NODE;
    }

    @Override
    default double progressInRasi(final double longitude) {
        return IDegreeUtils.round(d100 - IGraha.super.progressInRasi(longitude), 2);
    }

    @Override
    default double progressInBhava(final double longitude) {
        return IDegreeUtils.round(d100 - IGraha.super.progressInBhava(longitude), 2);
    }

    @Override
    default double progressInNaksatra(final double longitude) {
        return IDegreeUtils.round(d100 - IGraha.super.progressInNaksatra(longitude), 2);
    }

    @Override
    default double progressInNaksatraPada(final double longitude) {
        return IDegreeUtils.round(d100 - IGraha.super.progressInNaksatraPada(longitude), 2);
    }
}
