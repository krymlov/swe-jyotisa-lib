/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-01
 */
package org.jyotisa.app;

import org.jyotisa.api.IKundaliFields;
import org.jyotisa.api.IKundaliOptions;
import org.swisseph.ISwissEph;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweJulianDate;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweRuntimeException;
import swisseph.DblObj;

import static org.swisseph.ISwissEph.getCalendarType;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.utils.IDateUtils.F4Y_2M_2D_2H_2M_2S;
import static org.swisseph.utils.IDateUtils.format;
import static org.swisseph.utils.IDegreeUtils.toDMS;
import static org.swisseph.utils.IModuloUtils.fix360;
import static swisseph.SweConst.*;

/**
 * @author Yura Krymlov
 * @version 1.?, 2020-01
 */
public class KundaliFields implements IKundaliFields {
    private static final long serialVersionUID = -1179768600356946058L;

    private static final int RESERVED = 0;
    private static final int SDRL_TIME = 1; // sidereal time
    private static final int SUN_RISE = 2;
    private static final int SUN_SET = 3;
    private static final int SAVANDAY = 4;
    private static final int MOON_RISE = 5;
    private static final int MOON_SET = 6;

    private static final int DINMANA = 7;
    private static final int RATRIMANA = 8;
    private static final int MISHRAMAANKAALA = 9;
    private static final int ISHTAKAALA = 10;
    private static final int SURYA_SPASHTA = 11;

    private static final int BHAVA_LAGNA = 12;
    private static final int HORA_LAGNA = 13;
    private static final int GHATI_LAGNA = 14;

    protected final double[] fields = new double[GHATI_LAGNA + 1];
    protected final ISweObjects sweObjects;

    public KundaliFields(IKundaliOptions options, ISweObjects sweObjects) {
        this.sweObjects = sweObjects;
        calcMainFields();
    }

    protected void calcMainFields() {
        fields[SDRL_TIME] = sweObjects.swissEph().swe_sidtime(sweObjects.sweJulianDate()
                .julianDay() + sweObjects.sweLocation().longitude() / d360);
    }

    protected void calcSunRiseSet() {
        final StringBuilder serr = new StringBuilder(0);

        final DblObj dblobj = new DblObj();
        final DblObj dblobj1 = new DblObj();

        final ISwissEph swissEph = sweObjects.swissEph();
        final ISweJulianDate sweDate = sweObjects.sweJulianDate();
        final ISweGeoLocation geo = sweObjects.sweLocation();

        double julday = sweDate.julianDay();

        if (julday % 1.0D > d05) julday = (double) (int) julday + d05;
        else julday = (double) (int) julday - d05;

        // The Vedic sunrise and sunset & moon rise and moon set are as per the time 
        // when the center of the disc, i.e. half the disc, rises over the horizon.
        final int riseSetFlags = sweObjects.sweOptions().riseSetFlags();
        final int rsmi = SE_CALC_RISE | riseSetFlags;

        int result = swissEph.swe_rise_trans(julday, SE_SUN, null, SEFLG_SWIEPH, rsmi,
                geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);

        if (result < 0) throw new SweRuntimeException(serr.toString());

        if (sweDate.julianDay() < dblobj.val) {
            dblobj1.val = dblobj.val;
            julday--;
            result = swissEph.swe_rise_trans(julday, SE_SUN, null, SEFLG_SWIEPH, rsmi,
                    geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);
        } else {
            result = swissEph.swe_rise_trans(julday + d1, SE_SUN, null, SEFLG_SWIEPH, rsmi,
                    geo.coordinates(), geo.pressure(), geo.temperature(), dblobj1, serr);
        }

        if (result < 0) throw new SweRuntimeException(serr.toString());
        if (dblobj.val == d0) fixDblobj(dblobj, geo, julday);

        fields[SUN_RISE] = dblobj.val;

        if (dblobj1.val == d0 || dblobj.val == d0) fields[SAVANDAY] = -1D;
        else fields[SAVANDAY] = (dblobj1.val - dblobj.val) * d60;

        result = swissEph.swe_rise_trans(julday, SE_SUN, null, SEFLG_SWIEPH, SE_CALC_SET | riseSetFlags,
                geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);

        if (result < 0) throw new SweRuntimeException(serr.toString());
        if (dblobj.val == d0) fixDblobj(dblobj, geo, julday);

        fields[SUN_SET] = dblobj.val;
    }

    protected void calcMoonRiseSet() {
        final StringBuilder serr = new StringBuilder(0);
        final ISwissEph swissEph = sweObjects.swissEph();
        final ISweJulianDate sweDate = sweObjects.sweJulianDate();
        final double julday = sweDate.julianDay();
        final ISweGeoLocation geo = sweObjects.sweLocation();
        final DblObj dblobj = new DblObj();

        // The Vedic moon rise is as per the time when the half the disc, rises over the horizon.
        final int riseSetFlags = sweObjects.sweOptions().riseSetFlags();
        int rsmi = SE_CALC_RISE | riseSetFlags;

        int result = swissEph.swe_rise_trans(julday, SE_MOON, null, SEFLG_SWIEPH, rsmi,
                geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);

        if (result < 0) throw new SweRuntimeException(serr.toString());

        if (dblobj.val > julday) { // if rise time > birth time
            result = swissEph.swe_rise_trans(julday - d05, SE_MOON, null, SEFLG_SWIEPH, rsmi,
                    geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);

            if (dblobj.val > julday) { // if rise time > birth time
                result = swissEph.swe_rise_trans(julday - d1, SE_MOON, null, SEFLG_SWIEPH, rsmi,
                        geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);
            }
        }

        if (result < 0) throw new SweRuntimeException(serr.toString());
        if (dblobj.val == d0) fixDblobj(dblobj, geo, julday);

        fields[MOON_RISE] = dblobj.val;

        // The Vedic moon set is as per the time when the half the disc, sets over the horizon.
        rsmi = SE_CALC_SET | riseSetFlags;

        result = swissEph.swe_rise_trans(fields[MOON_RISE], SE_MOON, null, SEFLG_SWIEPH, rsmi,
                geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);

        if (result < 0) throw new SweRuntimeException(serr.toString());

        if (fields[MOON_RISE] > dblobj.val) {
            result = swissEph.swe_rise_trans(fields[MOON_RISE] + d05, SE_MOON, null, SEFLG_SWIEPH, rsmi,
                    geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);
        }

        if (result < 0) throw new SweRuntimeException(serr.toString());
        if (dblobj.val == d0) fixDblobj(dblobj, geo, julday);

        fields[MOON_SET] = dblobj.val;
    }

    protected void fixDblobj(final DblObj dblobj, final ISweGeoLocation geo, final double julday) {
        final int k = sweObjects.swissEph().swe_revjul(julday, getCalendarType(julday)).month();
        if (k > 3 && k < 10 && geo.latitude() < d0 || (k < 4 || k > 9) && geo.latitude() >= d0) {
            dblobj.val = -2D;
        } else {
            dblobj.val = -1D;
        }
    }

    protected void calcMiscFields() {
        if (fields[SUN_RISE] < d0 || fields[SUN_SET] < d0) {
            if (fields[SUN_RISE] < -1.2D || fields[SUN_SET] < -1.2D) fields[DINMANA] = d0;
            else fields[DINMANA] = d60;
        } else {
            fields[DINMANA] = (fields[SUN_SET] - fields[SUN_RISE]) * d60;
            if (fields[DINMANA] < d0) fields[DINMANA] += d60;
        }

        fields[RATRIMANA] = d60 - fields[DINMANA];
        fields[MISHRAMAANKAALA] = fields[DINMANA] + fields[RATRIMANA] / 2D;

        if (fields[SUN_RISE] < d0) {
            fields[ISHTAKAALA] = -1D;
            fields[SURYA_SPASHTA] = -1D;
            fields[BHAVA_LAGNA] = -1D;
            fields[HORA_LAGNA] = -1D;
            fields[GHATI_LAGNA] = -1D;
        } else {
            // Ishtakala is the time elapsed since the time of sunrise to the time of birth.
            final double julianDay = sweObjects.sweJulianDate().julianDay();
            final double birthTime = sweObjects.sweJulianDate().utime();
            final double sunrise = sunrise();
            double ishtaKaala = ((sunrise + d05) % 1.0D);

            ishtaKaala *= 24D;
            ishtaKaala = birthTime - ishtaKaala;
            ishtaKaala *= 2.5D;

            if (ishtaKaala < d0) ishtaKaala += d60;
            fields[ISHTAKAALA] = ishtaKaala;

            final double[] ad = new double[6];
            final StringBuilder serr = new StringBuilder(0);
            final int result = sweObjects.swissEph().swe_calc_ut(sunrise,
                    SE_SUN, sweObjects.sweOptions().calcFlags(), ad, serr);
            if (result == ERR) throw new SweRuntimeException(serr.toString());

            fields[SURYA_SPASHTA] =  fix360(ad[0]);

            final double jdsr = julianDay - sunrise;
            final double lsun = fields[SURYA_SPASHTA];

            fields[BHAVA_LAGNA] = fix360(360 * jdsr + lsun);
            fields[HORA_LAGNA] = fix360( 720 * jdsr + lsun);
            fields[GHATI_LAGNA] = fix360(1800 * jdsr + lsun);
        }
    }

    @Override
    public double siderealTime() {
        return fields[SDRL_TIME];
    }

    @Override
    public double moonrise() {
        if (0 == fields[MOON_RISE]) calcMoonRiseSet();
        return fields[MOON_RISE];
    }

    @Override
    public double moonset() {
        if (0 == fields[MOON_RISE]) calcMoonRiseSet();
        return fields[MOON_SET];
    }

    @Override
    public double sunrise() {
        if (0 == fields[SUN_RISE]) calcSunRiseSet();
        return fields[SUN_RISE];
    }

    @Override
    public double sunset() {
        if (0 == fields[SUN_SET]) calcSunRiseSet();
        return fields[SUN_SET];
    }

    @Override
    public double savanaDay() {
        if (0 == fields[SAVANDAY]) calcSunRiseSet();
        return fields[SAVANDAY];
    }

    @Override
    public double dinamana() {
        if (0 == fields[DINMANA]) calcMiscFields();
        return fields[DINMANA];
    }

    @Override
    public double ratrimana() {
        if (0 == fields[DINMANA]) calcMiscFields();
        return fields[RATRIMANA];
    }

    @Override
    public double mishramaanKaala() {
        if (0 == fields[DINMANA]) calcMiscFields();
        return fields[MISHRAMAANKAALA];
    }

    @Override
    public double ishtaKaala() {
        if (0 == fields[DINMANA]) calcMiscFields();
        return fields[ISHTAKAALA];
    }

    @Override
    public double suryaSpashta() {
        if (0 == fields[DINMANA]) calcMiscFields();
        return fields[SURYA_SPASHTA];
    }

    @Override
    public double bhavaLagna() {
        if (0 == fields[BHAVA_LAGNA]) calcMiscFields();
        return fields[BHAVA_LAGNA];
    }

    @Override
    public double horaLagna() {
        if (0 == fields[HORA_LAGNA]) calcMiscFields();
        return fields[HORA_LAGNA];
    }

    @Override
    public double ghatiLagna() {
        if (0 == fields[GHATI_LAGNA]) calcMiscFields();
        return fields[GHATI_LAGNA];
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(512);
        ISwissEph swissEph = sweObjects.swissEph();

        builder.append("Julian Day\t\t: ").append(sweObjects.sweJulianDate().julianDay())
                .append("\n Delta T\t\t: ").append((float)(sweObjects.sweJulianDate().deltaT() * d86400))
                .append("\nSidereal Time\t: ").append(toDMS(siderealTime(), true))
                .append("\nUTC Moonrise\t: ").append(format(swissEph.initDateTime(
                        new SweJulianDate(moonrise())), F4Y_2M_2D_2H_2M_2S))
                .append("\nUTC Moonset\t\t: ").append(format(swissEph.initDateTime(
                        new SweJulianDate(moonset())), F4Y_2M_2D_2H_2M_2S))
                .append("\nUTC Sunrise\t\t: ").append(format(swissEph.initDateTime(
                        new SweJulianDate(sunrise())), F4Y_2M_2D_2H_2M_2S))
                .append("\nUTC Sunset\t\t: ").append(format(swissEph.initDateTime(
                        new SweJulianDate(sunset())), F4Y_2M_2D_2H_2M_2S))
                /*.append("\nSurya Spashta\t: ").append(toDMS(suryaSpashta()))
                .append("\nIshta Kaala\t\t: ").append(toGPV(ishtaKaala()))
                .append("\nSavana Day\t\t: ").append(toGPV(savanaDay()))
                .append("\nDinamana\t\t: ").append(toGPV(dinamana()))
                .append("\nRatrimana\t\t: ").append(toGPV(ratrimana()))
                .append("\nMishramaan Kaala: ").append(toGPV(mishramaanKaala()))*/
                .append('\n');

        return builder.toString();
    }

    /**
     * <pre>
     * 1 ghati = 24 minutes = 60 pala, 1 pala = 60 vipala, 1 vipala = 60 prativipala
     * 1 day or 24 hours = 60 Ghatis
     * 1 Ghati = 60 Vighati (also called Pala or Kala)
     * 1 Vighati = 60 Lipta or (also called Vipala or Vikala)
     * 1 Lipta = 60 Vilipta
     * 1 Vilipta = 60 Para
     * 1 Para = 60 Tatpara
     * </pre>
     */
    protected static String toGPV(double dd) {
        dd += 0.5D / (dd < 0.0D ? -3600.0D : 3600.0D);
        int i = (int)(dd % 1.0D * 60.0D);
        int j = (int)(dd * 60.0D % 1.0D * 60.0D);
        return ((int)dd < 10 ? " " : "") + (int)dd
                + " Ghati " + (i < 10 ? "0" : "") + i
                + " Pala " + (j < 10 ? "0" : "") + j
                + " Vipala";
    }
}
