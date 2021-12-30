/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2020-06
*/

package org.jyotisa.gochara.rasi;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.rasi.IRasiGrahaEntity;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweSegment;

import static org.swisseph.api.ISweConstants.d0;
import static org.swisseph.api.ISweConstants.d180;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2020-06
 */
public class RasiChayaGrahaGochara extends RasiGrahaGochara {
    protected final boolean rahu;

    public RasiChayaGrahaGochara(IKundali kundali) {
        this(kundali, true);
    }
    
    public RasiChayaGrahaGochara(IKundali kundali, boolean forward) {
        this(kundali, true, forward);
    }

    public RasiChayaGrahaGochara(IKundali kundali, boolean rahu, boolean forward) {
        super(kundali, kundali.chayaGraha(rahu), forward);
        this.rahu = rahu;
    }
    
    @Override
    public IRasiGrahaEntity newTransitEntity() {
        double offset = transitCalc.getOffset();
        
        if ( ! rahu ) {
            offset += d180;
            offset = fix360(offset);
        }

        return new RasiGrahaEntity(offset, graha,
                ERasi.byLongitude(offset), julianDay);
    }
    
    @Override
    public double getStartOffset() {
        final double longitude = kundali.sweObjects().longitudes()[RA];
        final ISweSegment segment = ERasi.byLongitude(longitude).segment();
        return forward ? segment.close() : segment.start();
    }
    
    @Override
    public double getNextOffset(final IRasiGrahaEntity entity) {
        double offset = transitCalc.getOffset();
        if ( offsetStep == d0 ) return offset;
        if ( forward ) offset -= offsetStep;
        else offset += offsetStep;
        return fix360(offset);
    }

}
