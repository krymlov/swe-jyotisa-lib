/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.tithi;


import org.jyotisa.api.IKundali;
import org.jyotisa.app.KundaliSequenceIterator;
import org.swisseph.api.ISweSegment;
import swisseph.TCPlanetPlanet;
import swisseph.TransitCalculator;

import static swisseph.SweConst.SE_MOON;
import static swisseph.SweConst.SE_SUN;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public class TithiIterator extends KundaliSequenceIterator<TithiEntity> {

    public TithiIterator(IKundali kundali) {
        this(kundali, true);
    }
    
    public TithiIterator(IKundali kundali, boolean forward) {
        super(kundali, forward, kundali.panchanga().tithi().length());
    }
    
    @Override
    public TithiEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new TithiEntity(offset, ETithi.byOffset(offset), julianDay);
    }

    @Override
    public double getStartOffset() {
        final ISweSegment interval = kundali.panchanga().tithi().segment();
        return forward ? interval.start() : interval.close();
    }

    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanetPlanet(swissEph, SE_MOON, SE_SUN, transitCalcFlags(), startOffset);
    }

}
