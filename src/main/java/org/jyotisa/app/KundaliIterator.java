/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.app;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.IKundaliEntity;
import org.jyotisa.api.IKundaliIterator;
import org.swisseph.ISwissEph;
import swisseph.TransitCalculator;

import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.api.ISweJulianDate.JULDAY_MAX;
import static org.swisseph.api.ISweJulianDate.JULDAY_MIN;
import static org.swisseph.utils.IModuloUtils.fix360;
import static swisseph.SweConst.*;

/**
 * @author Yura Krymlov
 * @version 1.2, 2019-11
 */
public abstract class KundaliIterator<E extends IKundaliEntity<?>> implements IKundaliIterator<E> {
    protected final ISwissEph swissEph;
    protected final IKundali kundali;
    protected final boolean forward;
    protected final double offsetStep;

    protected TransitCalculator transitCalc;
    protected boolean alignTransit = true;
    protected double julianDay;

    protected KundaliIterator(final IKundali kundali, final boolean forward) {
        this(kundali, forward, d1);
    }

    protected KundaliIterator(final IKundali kundali, final boolean forward, final double offsetStep) {
        this.julianDay = kundali.sweJulianDate().julianDay();
        this.swissEph = kundali.swissEph();
        this.offsetStep = fix360(offsetStep);
        this.forward = forward;
        this.kundali = kundali;
    }

    @Override
    public int transitCalcFlags() {
        return SEFLG_TRANSIT_LONGITUDE | ((kundali.sweOptions().calcFlags() ^ SEFLG_NONUT) ^ SEFLG_SPEED);
    }

    protected abstract TransitCalculator createTransitCalc(final double startOffset);
    protected abstract double getStartOffset();
    protected abstract E newTransitEntity();

    /*
    public KundaliIterator<E> setOffsetStep(final double offsetStep) {
        this.offsetStep = fix360(offsetStep);
        return this;
    }*/

    protected double getNextOffset(final E entity) {
        double offset = entity.longitude();
        if ( offsetStep == d0 ) return offset;
        if ( forward ) offset += offsetStep;
        else offset -= offsetStep;
        return fix360(offset);
    }

    public double getOffsetStep() {
        return offsetStep;
    }

    public boolean isForward() {
        return forward;
    }

    /**
     * By default initiates/aligns transit calculation for the given event date, 
     * means return the event date/degree as the first next() value.<br>
     * <br>
     * In many cases it is mandatory, but if you need to disable then set alignTransit=false<br>
     * This method automatically set alignTransit=false at the end of execution
     */
    protected void calcFirstTransit() {
        julianDay = forward ? (julianDay - DELTA_D0000001) : (julianDay + DELTA_D0000001);
        julianDay = TransitCalculator.getTransitUT(transitCalc, julianDay, forward);
        alignTransit = false;
    }
    
    /**
     * Continues transit calculation using next offset
     */
    protected void calcNextTransit() {
        julianDay = forward ? (julianDay + DELTA_D0000001) : (julianDay - DELTA_D0000001);
        julianDay = TransitCalculator.getTransitUT(transitCalc, julianDay, !forward);
    }
    
    @Override
    public boolean hasNext() {
        return !(julianDay <= JULDAY_MIN) && !(julianDay >= JULDAY_MAX);
    }

    @Override
    public E next() {
        if ( null == transitCalc ) {
            transitCalc = createTransitCalc(getStartOffset());
        }
            
        if ( alignTransit ) {
            calcFirstTransit();
        } else {
            calcNextTransit();
        }
        
        final E entity = newTransitEntity();
        transitCalc.setOffset(getNextOffset(entity));

        return entity;
    }

    protected TransitCalculator getTransitCalc() {
        return transitCalc;
    }
}
