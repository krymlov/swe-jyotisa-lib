/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara.rasi;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.rasi.IRasiEntity;
import org.jyotisa.app.KundaliSequenceIterator;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweSegment;
import swisseph.TCHouses;
import swisseph.TransitCalculator;

import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.api.ISweObjects.LG;
import static swisseph.SweConst.SEFLG_TRUEPOS;
import static swisseph.SweConst.SE_ASC;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public class RasiLagnaGochara extends KundaliSequenceIterator<IRasiEntity> {

    public RasiLagnaGochara(IKundali kundali) {
        this(kundali, true);
    }

    public RasiLagnaGochara(IKundali kundali, boolean forward) {
        super(kundali, forward, RASI_LENGTH);
    }

    @Override
    public IRasiEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new RasiEntity(offset, ERasi.byLongitude(offset), julianDay);
    }

    @Override
    public double getStartOffset() {
        final double degree = kundali.sweObjects().longitudes()[LG];
        final ISweSegment segment = ERasi.byLongitude(degree).segment();
        return forward ? segment.start() : segment.close();
    }
    
    /**
     * The ascendant is a geometric point, not a body, so the true-versus-apparent distinction
     * {@code SEFLG_TRUEPOS} expresses does not apply to it - the flag is cleared before the
     * search.
     * <p>
     * This used to be {@code ^ SEFLG_TRUEPOS}, which <b>toggles</b> rather than clears. It
     * happened to do the right thing only because {@code DEFAULT_SS_TRANSIT_FLAGS} carries
     * the bit: the moment a caller passes transit flags without it - which
     * {@code SweObjectsOptions.Builder.transitFlags(...)} allows - the XOR would have
     * switched it <b>on</b>, i.e. exactly the opposite of the intent, silently. Toggling a
     * caller-supplied flag is never a meaningful contract; clearing it is.
     */
    @Override
    public int transitCalcFlags() {
        return super.transitCalcFlags() & ~SEFLG_TRUEPOS;
    }

    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        final ISweGeoLocation geo = kundali.sweLocation();
        
        // check if within polar circle ??? in this case just move forward/backward
        if ( geo.withinPolarCircle() ) alignTransit = false;
        
        return new TCHouses(swissEph, SE_ASC, kundali.sweOptions().houseSystem().fid(),
            geo.longitude(), geo.latitude(), transitCalcFlags(), startOffset);
    }
}
