/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.app.KundaliIterator;
import org.jyotisa.app.KundaliRuntimeException;
import org.jyotisa.graha.lagna.GrahaLagna;
import swisseph.TCPlanet;
import swisseph.TransitCalculator;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.jyotisa.graha.chaya.GrahaKetu.KETU;
import static org.jyotisa.graha.chaya.GrahaRahu.RAHU;
import static org.swisseph.api.ISweConstants.DELTA_D0000001;
import static org.swisseph.api.ISweConstants.d1;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class GrahaGochara extends KundaliIterator<GrahaEntity> {
    protected static final String LG_RA_KE_IS_NOT_SUPPORTED = "LG|RA|KE is not supported";

    protected final IGraha graha;
    protected boolean alignToOneDegree;
    protected boolean calcPreviousTransit;

    public GrahaGochara(IKundali kundali) {
        this(kundali, true);
    }
    
    public GrahaGochara(IKundali kundali, boolean forward) {
        this(kundali, CHANDRA, forward);
    }

    public GrahaGochara(IKundali kundali, IGraha graha, boolean forward) {
        this(kundali, graha, forward, d1);
    }

    public GrahaGochara(IKundali kundali, IGraha graha, boolean forward, final double offsetStep) {
        this(kundali, forward, graha, offsetStep);

        final int fid = graha.fid();
        if (RAHU.fid() == fid || KETU.fid() == fid || GrahaLagna.LAGNA.fid() == fid) {
            throw new KundaliRuntimeException(LG_RA_KE_IS_NOT_SUPPORTED);
        }
    }

    public GrahaGochara(IKundali kundali, boolean forward, IGraha graha, final double offsetStep) {
        super(kundali, forward, offsetStep);
        this.graha = graha;
    }

    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanet(swissEph, graha.swefid(), transitCalcFlags(), startOffset);
    }
    
    @Override
    public GrahaEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new GrahaEntity(offset, graha, julianDay);
    }

    @Override
    public double getStartOffset() {
        final double offset = kundali.sweObjects().longitudes()[graha.uid()];
        if ( ! alignToOneDegree ) return offset; 
        if ( forward ) return (int)offset;
        return fix360(Math.ceil(offset));
    }
    
    @Override
    protected void calcFirstTransit() {
        if ( calcPreviousTransit ) {
            super.calcFirstTransit();
        } else {   
            julianDay = forward ? (julianDay - DELTA_D0000001) : (julianDay + DELTA_D0000001);
            julianDay = TransitCalculator.getTransitUT(transitCalc, julianDay, ! forward);
            alignTransit = false;
        }
    }

    public IGraha getGraha() {
        return graha;
    }

    public boolean alignToOneDegree() {
        return alignToOneDegree;
    }

    public boolean calcPreviousTransit() {
        return calcPreviousTransit;
    }

    public GrahaGochara setCalcPreviousTransit(boolean calcPreviousTransit) {
        this.calcPreviousTransit = calcPreviousTransit;
        return this;
    }

    public GrahaGochara setAlignToOneDegree(boolean alignToOneDegree) {
        this.alignToOneDegree = alignToOneDegree;
        return this;
    }
    
}
