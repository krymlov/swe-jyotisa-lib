/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.nityayoga;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.nityayoga.INityaYogaEntity;
import org.jyotisa.app.KundaliSequenceIterator;
import org.swisseph.api.ISweSegment;
import swisseph.TCPlanetPlanet;
import swisseph.TransitCalculator;

import static org.swisseph.api.ISweConstants.DSTEP_ROUND;
import static swisseph.SweConst.*;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public class NityaYogaIterator extends KundaliSequenceIterator<INityaYogaEntity> {

    public NityaYogaIterator(IKundali kundali) {
        this(kundali, true);
    }

    public NityaYogaIterator(IKundali kundali, boolean forward) {
        super(kundali, forward, kundali.panchanga().yoga().length());
    }

    @Override
    public INityaYogaEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new NityaYogaEntity(offset, ENityaYoga.byOffset(offset), julianDay);
    }

    @Override
    public int transitCalcFlags() {
        return super.transitCalcFlags() | SEFLG_YOGA_TRANSIT;
    }

    @Override
    public double getStartOffset() {
        final ISweSegment interval = kundali.panchanga().yoga().segment();
        return forward ? interval.start() : interval.close();
    }

    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanetPlanet(swissEph, SE_MOON, SE_SUN, transitCalcFlags(), startOffset);
    }

    @Override
    public double getNextOffset(final INityaYogaEntity entity) {
        double d = super.getNextOffset(entity);
        d += DSTEP_ROUND;
        return d;
    }

}
