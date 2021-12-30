/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.app.KundaliRuntimeException;
import org.jyotisa.graha.chandra.GrahaChandra;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.jyotisa.graha.chaya.GrahaKetu.KETU;
import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;
import static org.jyotisa.graha.lagna.GrahaLagna.LAGNA;
import static org.swisseph.api.ISweConstants.d0;

/**
 * @author Yura Krymlov
 * @version 1.?, 2019-12
 */
public class GrahaGocharaSpecial extends GrahaGochara {
    protected final double longitude;

    /**
     * @param longitude The longitude that will be transited by the {@link GrahaChandra}.
     */
    public GrahaGocharaSpecial(IKundali kundali, double longitude) {
        this(kundali, longitude, CHANDRA, true);
    }

    /**
     * @param longitude The longitude that will be transited by the {@link GrahaChandra}.
     */
    public GrahaGocharaSpecial(IKundali kundali, double longitude, boolean forward) {
        this(kundali, longitude, CHANDRA, forward);
    }

    /**
     * @param firstGraha The longitude of the first graha that will be transited by the second graha
     */
    public GrahaGocharaSpecial(IKundali kundali, IGraha firstGraha, IGraha secondGraha, boolean forward) {
        this(kundali, kundali.sweObjects().longitudes()[firstGraha.uid()], secondGraha, forward);
    }

    /**
     * @param longitude The longitude that will be transited by the given {@link IGraha}.
     */
    public GrahaGocharaSpecial(IKundali kundali, double longitude, IGraha graha, boolean forward) {
        super(kundali, forward, graha, d0);
        this.longitude = longitude;

        final int fid = graha.fid();
        if (RAHU.fid() == fid || KETU.fid() == fid || LAGNA.fid() == fid) {
            throw new KundaliRuntimeException(LG_RA_KE_IS_NOT_SUPPORTED);
        }
    }

    @Override
    public double getStartOffset() {
        return longitude;
    }

}
