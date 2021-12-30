/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara;


import org.jyotisa.api.IKundali;

import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class ChayaGrahaGochara extends GrahaGochara {
    protected static final String RA_KE_IS_SUPPORTED_ONLY = "RA|KE is supported only";
    
    protected final boolean rahu;

    public ChayaGrahaGochara(IKundali kundali) {
        this(kundali, true);
    }
    
    public ChayaGrahaGochara(IKundali kundali, boolean forward) {
        this(kundali, true, d1, forward);
    }
    
    public ChayaGrahaGochara(IKundali kundali, boolean rahu, final double offsetStep, boolean forward) {
        super(kundali, forward, kundali.chayaGraha(rahu), offsetStep);
        this.rahu = rahu;
    }

    @Override
    public GrahaEntity newTransitEntity() {
        double offset = transitCalc.getOffset();
        
        if ( ! rahu ) {
            offset += d180;
            offset = fix360(offset);
        }
        
        return new GrahaEntity(offset, graha, julianDay);
    }
    
    @Override
    public double getStartOffset() {
        final double offset = kundali.sweObjects().longitudes()[RA];
        if ( ! alignToOneDegree ) return offset; 
        if ( ! forward ) return (int)offset;
        return fix360(Math.ceil(offset));
    }
    
    @Override
    public double getNextOffset(final GrahaEntity entity) {
        double offset = transitCalc.getOffset();
        if ( offsetStep == d0 ) return offset;
        if ( forward ) offset -= offsetStep;
        else offset += offsetStep;
        return fix360(offset);
    }

}
