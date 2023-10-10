/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.naksatra.INaksatraPadaEntity;
import org.jyotisa.api.naksatra.INaksatraPadaGrahaEntity;
import org.jyotisa.app.KundaliSequenceIterator;
import org.jyotisa.naksatra.ENaksatraPada;
import org.swisseph.api.ISweSegment;
import swisseph.TCPlanet;
import swisseph.TransitCalculator;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.swisseph.api.ISweConstants.DSTEP_ROUND;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-11
 */
public class NaksatraPadaGrahaGochara extends KundaliSequenceIterator<INaksatraPadaGrahaEntity> {
    protected final IGraha graha;

    public NaksatraPadaGrahaGochara(IKundali kundali) {
        this(kundali, true);
    }
    
    public NaksatraPadaGrahaGochara(IKundali kundali, boolean forward) {
        this(kundali, CHANDRA, forward);
    }
    
    public NaksatraPadaGrahaGochara(final IKundali kundali, final IGraha graha, boolean forward) {
        this(kundali, graha, forward, kundali.panchanga().pada().length());
    }

    public NaksatraPadaGrahaGochara(final IKundali kundali, final IGraha graha, boolean forward, final double offsetStep) {
        super(kundali, forward, offsetStep);
        this.graha = graha;
    }

    @Override
    public INaksatraPadaGrahaEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        final INaksatraPada eenum = ENaksatraPada.byLongitude(offset);
        return new NaksatraPadaGrahaEntity(offset, graha, eenum, julianDay);
    }

    @Override
    public double getStartOffset() {
        final double longitude = kundali.sweObjects().longitudes()[graha.uid()];
        final ISweSegment interval = ENaksatraPada.byLongitude(longitude).segment();
        return forward ? interval.start() : interval.close();
    }

    @Override
    public double getNextOffset(final INaksatraPadaGrahaEntity entity) {
        double d = super.getNextOffset(entity);
        d += DSTEP_ROUND;
        return d;
    }

    @Override
    public TransitCalculator createTransitCalc(double startOffset) {
        return new TCPlanet(swissEph, graha.swefid(), transitCalcFlags(), startOffset);
    }
    

    public IGraha getGraha() {
        return graha;
    }
}
