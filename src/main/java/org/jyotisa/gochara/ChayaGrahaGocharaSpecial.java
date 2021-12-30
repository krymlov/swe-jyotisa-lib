/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2020-01
*/

package org.jyotisa.gochara;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.graha.chaya.GrahaRahu;

import static org.swisseph.api.ISweConstants.d0;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2020-01
 */
public class ChayaGrahaGocharaSpecial extends ChayaGrahaGochara {
    protected final double longitude;

    /**
     * @param longitude The longitude that will be transited by the {@link GrahaRahu}.
     */
    public ChayaGrahaGocharaSpecial(IKundali kundali, double longitude) {
        this(kundali, false, longitude, true);
    }
    
    /**
     * @param longitude The longitude that will be transited by the {@link GrahaRahu}.
     */
    public ChayaGrahaGocharaSpecial(IKundali kundali, double longitude, boolean forward) {
        this(kundali, true, longitude, forward);
    }
    
    /**
     * @param graha The longitude of the graha that will be transited by the chaya graha
     */
    public ChayaGrahaGocharaSpecial(IKundali kundali, boolean rahu, IGraha graha, boolean forward) {
        this(kundali, rahu, kundali.sweObjects().longitudes()[graha.uid()], forward);
    }
    
    /**
     * @param longitude The longitude that will be transited by the chaya graha
     */
    public ChayaGrahaGocharaSpecial(IKundali kundali, boolean rahu, double longitude, boolean forward) {
        super(kundali, rahu, d0, forward);
        this.longitude = longitude;
    }
    
    @Override
    public double getStartOffset() {
        return longitude;
    }
}
