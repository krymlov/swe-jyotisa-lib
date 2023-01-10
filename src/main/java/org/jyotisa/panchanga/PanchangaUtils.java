/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2020-03
*/

package org.jyotisa.panchanga;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.karana.IKaranaEntity;
import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.naksatra.INaksatraEntity;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.naksatra.INaksatraPadaEntity;
import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.nityayoga.INityaYogaEntity;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.api.tithi.ITithiEntity;
import org.jyotisa.gochara.naksatra.NaksatraEntity;
import org.jyotisa.gochara.naksatra.NaksatraPadaEntity;
import org.jyotisa.karana.EKarana;
import org.jyotisa.karana.KaranaEntity;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.naksatra.ENaksatraPada;
import org.jyotisa.nityayoga.ENityaYoga;
import org.jyotisa.nityayoga.NityaYogaEntity;
import org.jyotisa.tithi.ETithi;
import org.jyotisa.tithi.TithiEntity;
import org.swisseph.ISwissEph;
import org.swisseph.api.ISweSegment;
import swisseph.TCPlanet;
import swisseph.TCPlanetPlanet;
import swisseph.TransitCalculator;

import static org.jyotisa.graha.chandra.GrahaChandra.CHANDRA;
import static org.swisseph.api.ISweConstants.d2;
import static swisseph.SweConst.*;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2020-03
 */
public class PanchangaUtils {
    protected PanchangaUtils() {
    }

    public static int transitCalcFlags(final IKundali kundali) {
        return kundali.sweOptions().transitFlags();
    }
    
    public static ITithiEntity[] getTransitOfTithi(IKundali kundali, ITithi tithi) {
        final ISwissEph swissEph = kundali.swissEph();
        final ISweSegment interval = tithi.segment();
        
        final TransitCalculator tc = new TCPlanetPlanet(swissEph, SE_MOON, SE_SUN,
                transitCalcFlags(kundali), interval.close());

        ITithiEntity tithiEnd = newTithiEntity(tc, TransitCalculator
            .getTransitUT(tc, kundali.sweJulianDate().julianDay(), false));
        
        tc.setOffset(interval.start());
        ITithiEntity tithiStart = newTithiEntity(tc, TransitCalculator
            .getTransitUT(tc, tithiEnd.julianDay(), true));

        return new ITithiEntity[] { tithiStart, tithiEnd };
    }
    
    public static INaksatraEntity[] getTransitOfNaksatra(IKundali kundali, INaksatra naksatra) {
        final ISwissEph swissEph = kundali.swissEph();
        final ISweSegment interval = naksatra.segment();
        
        final TransitCalculator tc = new TCPlanet(swissEph, SE_MOON,
                transitCalcFlags(kundali), interval.close());

        INaksatraEntity naksatraEnd = newNaksatraEntity(tc, TransitCalculator
            .getTransitUT(tc, kundali.sweJulianDate().julianDay(), false));
        
        tc.setOffset(interval.start());
        INaksatraEntity naksatraStart = newNaksatraEntity(tc, TransitCalculator
            .getTransitUT(tc, naksatraEnd.julianDay(), true));

        return new INaksatraEntity[] { naksatraStart, naksatraEnd };
    }
    
    public static INaksatraPadaEntity[] getTransitOfNaksatraPada(IKundali kundali, INaksatraPada naksatraPada) {
        final ISwissEph swissEph = kundali.swissEph();
        final ISweSegment interval = naksatraPada.segment();
        
        final TransitCalculator tc = new TCPlanet(swissEph, SE_MOON,
                transitCalcFlags(kundali), interval.close());

        INaksatraPadaEntity naksatraPadaEnd = newNaksatraPadaEntity(tc, TransitCalculator
            .getTransitUT(tc, kundali.sweJulianDate().julianDay(), false));
        
        tc.setOffset(interval.start());
        INaksatraPadaEntity naksatraPadaStart = newNaksatraPadaEntity(tc, TransitCalculator
            .getTransitUT(tc, naksatraPadaEnd.julianDay(), true));

        return new INaksatraPadaEntity[] { naksatraPadaStart, naksatraPadaEnd };
    }
    
    public static INityaYogaEntity[] getTransitOfNityaYoga(IKundali kundali, INityaYoga nityaYoga) {
        final ISwissEph swissEph = kundali.swissEph();
        final ISweSegment interval = nityaYoga.segment();
        
        final TransitCalculator tc = new TCPlanetPlanet(swissEph, SE_MOON, SE_SUN,
                transitCalcFlags(kundali) | SEFLG_YOGA_TRANSIT, interval.close());

        INityaYogaEntity nityaYogaEnd = newNityaYogaEntity(tc, TransitCalculator
            .getTransitUT(tc, kundali.sweJulianDate().julianDay(), false));
        
        tc.setOffset(interval.start());
        INityaYogaEntity nityaYogaStart = newNityaYogaEntity(tc, TransitCalculator
            .getTransitUT(tc, nityaYogaEnd.julianDay(), true));

        return new INityaYogaEntity[] { nityaYogaStart, nityaYogaEnd };
    }
    
    public static IKaranaEntity[] getTransitOfKarana(IKundali kundali, ITithi tithi) {
        final ISwissEph swissEph = kundali.swissEph();
        final ISweSegment interval = tithi.segment();
        
        final TransitCalculator tc = new TCPlanetPlanet(swissEph, SE_MOON, SE_SUN,
                transitCalcFlags(kundali), interval.close());

        IKaranaEntity karanaEnd = newKaranaEntity(tc, TransitCalculator
            .getTransitUT(tc, kundali.sweJulianDate().julianDay(), false));
        
        tc.setOffset((interval.start() + interval.close())/d2);
        IKaranaEntity karanaMiddle = newKaranaEntity(tc, TransitCalculator
            .getTransitUT(tc, karanaEnd.julianDay(), true));
        
        tc.setOffset(interval.start());
        IKaranaEntity karanaStart = newKaranaEntity(tc, TransitCalculator
            .getTransitUT(tc, karanaMiddle.julianDay(), true));

        return new IKaranaEntity[] { karanaStart, karanaMiddle, karanaEnd };
    }
    
    protected static ITithiEntity newTithiEntity(TransitCalculator transitCalc, final double julday) {
        final double offset = transitCalc.getOffset();
        return new TithiEntity(offset, ETithi.byOffset(offset), julday);
    }
    
    protected static IKaranaEntity newKaranaEntity(TransitCalculator transitCalc, final double julday) {
        final double offset = transitCalc.getOffset();
        return new KaranaEntity(offset, EKarana.byOffset(offset), julday);
    }

    protected static INaksatraEntity newNaksatraEntity(TransitCalculator transitCalc, final double julday) {
        final double offset = transitCalc.getOffset();
        return new NaksatraEntity(offset, CHANDRA, ENaksatra.byLongitude(offset), julday);
    }
    
    protected static INityaYogaEntity newNityaYogaEntity(TransitCalculator transitCalc, final double julday) {
        final double offset = transitCalc.getOffset();
        return new NityaYogaEntity(offset, ENityaYoga.byOffset(offset), julday);
    }
    
    protected static INaksatraPadaEntity newNaksatraPadaEntity(TransitCalculator transitCalc, final double julday) {
        final double offset = transitCalc.getOffset();
        return new NaksatraPadaEntity(offset, CHANDRA, ENaksatraPada.byLongitude(offset), julday);
    }
}
