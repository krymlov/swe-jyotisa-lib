/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara.rasi;


import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasiGrahaEntity;
import org.jyotisa.app.KundaliSequenceIterator;
import org.jyotisa.rasi.ERasi;
import org.swisseph.api.ISweSegment;
import swisseph.TCPlanet;
import swisseph.TransitCalculator;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class RasiGrahaGochara extends KundaliSequenceIterator<IRasiGrahaEntity> {
    protected final IGraha graha;

    public RasiGrahaGochara(IKundali kundali) {
        this(kundali, true);
    }
    
    public RasiGrahaGochara(IKundali kundali, boolean forward) {
        this(kundali, CHANDRA, forward);
    }
    
    public RasiGrahaGochara(IKundali kundali, IGraha graha, boolean forward) {
        this(kundali, graha, forward, RASI_LENGTH);
    }

    public RasiGrahaGochara(IKundali kundali, IGraha graha, boolean forward, final double offsetStep) {
        super(kundali, forward, offsetStep);
        this.graha = graha;
    }

    @Override
    public IRasiGrahaEntity newTransitEntity() {
        final double offset = transitCalc.getOffset();
        return new RasiGrahaEntity(offset, graha, ERasi.byLongitude(offset), julianDay);
    }

    @Override
    public double getStartOffset() {
        final double longitude = kundali.sweObjects().longitudes()[graha.uid()];
        final ISweSegment interval = ERasi.byLongitude(longitude).segment();
        return forward ? interval.start() : interval.close();
    }

    @Override
    public TransitCalculator createTransitCalc(final double startOffset) {
        return new TCPlanet(swissEph, graha.swefid(), transitCalcFlags(), startOffset);
    }
    
    public IGraha getGraha() {
        return graha;
    }
}
