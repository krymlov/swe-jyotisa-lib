/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.karana;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.karana.IKaranaEntity;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.app.KundaliSequenceIterator;
import org.swisseph.api.ISweSegment;
import org.swisseph.utils.IModuloUtils;
import swisseph.TCPlanetPlanet;
import swisseph.TransitCalculator;

import static org.swisseph.api.ISweConstants.d2;
import static swisseph.SweConst.SE_MOON;
import static swisseph.SweConst.SE_SUN;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public class KaranaIterator extends KundaliSequenceIterator<IKaranaEntity> {
    
    protected boolean startFrom2ndKaranaIfActual;
    protected boolean karana2nd;

    public KaranaIterator(IKundali kundali) {
        this(kundali, false, true);
    }
    
    public KaranaIterator(IKundali kundali, boolean forward) {
        this(kundali, false, forward);
    }
    
    /**
     * @param startFrom2ndKaranaIfActual allow to start iteration from 2nd karana if it is actual
     */
    public KaranaIterator(IKundali kundali, boolean startFrom2ndKaranaIfActual, boolean forward) {
        super(kundali, forward, kundali.panchanga().karana().length());
        this.startFrom2ndKaranaIfActual = startFrom2ndKaranaIfActual;
        this.transitCalc = createTransitCalc(getStartOffset());
    }

    @Override
    public IKaranaEntity next() {
        IKaranaEntity entity = super.next();
        karana2nd = !karana2nd;
        return entity;
    }
    
    @Override
    public IKaranaEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new KaranaEntity(offset, EKarana.byOffset(offset), julianDay);
    }

    @Override
    public double getStartOffset() {
        final IPanchanga panchanga = kundali.panchanga();
        
        final ITithi tithi = panchanga.tithi();
        final ISweSegment tithiLongs = tithi.segment();
        
        if ( ! startFrom2ndKaranaIfActual ) {
            // return end or start of the tithi degree
            return forward ? tithiLongs.start() : tithiLongs.close();
        }
        
        final double chsyOffset = IModuloUtils.fix360(
            panchanga.chandraLongitude() -
                panchanga.suryaLongitude());
        
        double offset = (tithiLongs.start() + tithiLongs.close())/d2;
        
        if ( forward ) {
            if ( offset > chsyOffset ) {
                offset = tithiLongs.start();
            } else {
                karana2nd = true;
            }
        } else {
            if ( offset < chsyOffset ) {
                offset = tithiLongs.close();
            } else {
                karana2nd = true;
            }
        }

        return offset;
    }
    
    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanetPlanet(swissEph, SE_MOON, SE_SUN, transitCalcFlags(), startOffset);
    }

}
