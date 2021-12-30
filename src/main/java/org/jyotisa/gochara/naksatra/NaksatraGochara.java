/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.naksatra.INaksatraEntity;
import org.jyotisa.app.KundaliSequenceIterator;
import org.swisseph.api.ISweSegment;
import swisseph.TCPlanet;
import swisseph.TransitCalculator;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.jyotisa.naksatra.ENaksatra.byLongitude;
import static org.swisseph.api.ISweConstants.DSTEP_ROUND;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public class NaksatraGochara extends KundaliSequenceIterator<INaksatraEntity> {
    protected final IGraha graha;

    public NaksatraGochara(IKundali kundali) {
        this(kundali, true);
    }
    
    public NaksatraGochara(IKundali kundali, boolean forward) {
        this(kundali, CHANDRA, forward);
    }
    
    public NaksatraGochara(final IKundali kundali, final IGraha graha, boolean forward) {
        this(kundali, graha, forward, kundali.panchanga().pada().naksatra().length());
    }

    public NaksatraGochara(final IKundali kundali, final IGraha graha, boolean forward, final double offsetStep) {
        super(kundali, forward, offsetStep);
        this.graha = graha;
    }

    @Override
    public INaksatraEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new NaksatraEntity(offset, graha, byLongitude(offset), julianDay);
    }

    @Override
    public double getStartOffset() {
        final double longitude = kundali.sweObjects().longitudes()[graha.uid()];
        final ISweSegment interval = byLongitude(longitude).segment();
        return forward ? interval.start() : interval.close();
    }

    @Override
    public double getNextOffset(final INaksatraEntity entity) {
        double d = super.getNextOffset(entity);
        d += DSTEP_ROUND;
        return d;
    }

    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanet(swissEph, graha.swefid(), transitCalcFlags(), startOffset);
    }
    
}
