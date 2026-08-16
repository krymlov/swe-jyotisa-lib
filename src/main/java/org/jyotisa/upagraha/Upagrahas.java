/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.IKundaliOptions;
import org.jyotisa.api.upagraha.IUpagraha;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.upagraha.IUpagrahas;
import org.swisseph.ISwissEph;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweRuntimeException;
import swisseph.DblObj;

import static org.jyotisa.upagraha.EUpagraha.*;
import static org.jyotisa.upagraha.UpagrahaArthaprahaara.ART;
import static org.jyotisa.upagraha.UpagrahaDhuma.DHU;
import static org.jyotisa.upagraha.UpagrahaGulika.GLK;
import static org.jyotisa.upagraha.UpagrahaIndrachaapa.CHP;
import static org.jyotisa.upagraha.UpagrahaKaala.KAA;
import static org.jyotisa.upagraha.UpagrahaMaandi.MND;
import static org.jyotisa.upagraha.UpagrahaMrityu.MRT;
import static org.jyotisa.upagraha.UpagrahaParivesha.PAR;
import static org.jyotisa.upagraha.UpagrahaUpaketu.UPK;
import static org.jyotisa.upagraha.UpagrahaVyatipaata.VYA;
import static org.jyotisa.upagraha.UpagrahaYamaghantaka.YAM;
import static org.swisseph.api.ISweConstants.*;
import static org.swisseph.api.ISweObjects.*;
import static org.swisseph.utils.IModuloUtils.fix360;
import static org.swisseph.utils.IModuloUtils.modulo;
import static swisseph.SweConst.ERR;
import static swisseph.SweConst.SEFLG_SWIEPH;
import static swisseph.SweConst.SE_ASC;
import static swisseph.SweConst.SE_CALC_RISE;
import static swisseph.SweConst.SE_CALC_SET;
import static swisseph.SweConst.SE_SUN;
import static swisseph.SweDate.getDayOfWeekNr;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class Upagrahas implements IUpagrahas {
    private static final long serialVersionUID = -7745657850507246704L;
    protected final IUpagrahaEntity[] all = new UpagrahaEntity[MAANDI.uid() + 1];

    public Upagrahas(final IKundaliOptions options, final ISweObjects sweObjects) {
        calcMainUpagrahas(sweObjects);
        calcKalavelaUpagrahas(sweObjects);
    }

    protected void calcMainUpagrahas(ISweObjects sweObjects) {
        final double suryaLongitude = sweObjects.longitudes()[SY];

        // Sun's longitude + 133º 20'
        double degree = fix360(suryaLongitude + d133 + dd20);
        all[DHUMA.uid()] = new UpagrahaEntity(DHU, sweObjects, degree);

        // 360º – Dhuma’s longitude
        degree = fix360(d360 - degree);
        all[VYATIPAATA.uid()] = new UpagrahaEntity(VYA, sweObjects, degree);

        // Vyatipata's longitude + 180
        degree = fix360(degree + d180);
        all[PARIVESHA.uid()] = new UpagrahaEntity(PAR, sweObjects, degree);

        // 360º – Parivesha’s longitude
        degree = fix360(d360 - degree);
        all[INDRACHAAPA.uid()] = new UpagrahaEntity(CHP, sweObjects, degree);

        // Indrachaapa’s longitude + 16º 40' (or) Sun's longitude – 30
        degree = fix360(degree + d16 + dd40);
        all[UPAKETU.uid()] = new UpagrahaEntity(UPK, sweObjects, degree);
    }

    /**
     * Kaala, Mrityu, Arthaprahaara, Yamaghantaka, Gulika and Maandi ("Kalavela" upagrahas) are
     * not fixed offsets from the Sun like the five above - each is the Ascendant rising at a
     * specific moment derived from dividing the day (or night, for a night birth) into eight
     * equal parts (BPHS 3.68 for Gulika; the same construction is classically used for the other
     * five). The first seven parts are ruled, in order starting from the birth weekday's own
     * lord, by Sun, Moon, Mars, Mercury, Jupiter, Venus, Saturn - the plain weekday sequence
     * itself, which is why {@link ISweObjects}'s chart indices SY..SA (1..7) line up directly
     * with weekday 0..6 (Sunday..Saturday, {@link swisseph.SweDate#getDayOfWeekNr}'s convention).
     * The eighth part has no lord. A night birth's eight parts follow the same seven-lord
     * sequence but shifted 4 parts further along (equivalently: night part i has the lord day
     * part i+4 would have had) - verified against BPHS's own examples, "Gulika is the 7th part
     * of daytime, or the 3rd part of nighttime", for a Sunday birth.
     * <p>
     * Kaala/Mrityu/Arthaprahaara/Yamaghantaka/Gulika all rise at the <b>middle</b> of their
     * ruling planet's part; Maandi rises at its <b>beginning</b> - see {@link IUpagrahas}.
     */
    protected void calcKalavelaUpagrahas(final ISweObjects sweObjects) {
        final double birthUt = sweObjects.sweJulianDate().julianDay();

        final double lastSunrise = findLastRiseOrSetAtOrBefore(sweObjects, SE_CALC_RISE, birthUt);
        final double lastSunset = findLastRiseOrSetAtOrBefore(sweObjects, SE_CALC_SET, birthUt);

        // day birth: the most recent of the two events was a sunrise, so we are still before
        // that day's sunset; night birth: the other way round
        final boolean dayBirth = lastSunrise > lastSunset;
        final double periodStart = dayBirth ? lastSunrise : lastSunset;
        final double periodEnd = findRiseOrSet(sweObjects, dayBirth ? SE_CALC_SET : SE_CALC_RISE, periodStart);
        final double portionLength = (periodEnd - periodStart) / d8;

        // civil weekday of the birth instant itself - the same value Kundali.panchanga() already
        // uses for the printed Vaara, so the two never disagree even though a purist Panchanga
        // would arguably carry the previous day's weekday through until the next sunrise
        final int weekday = getDayOfWeekNr(birthUt);

        all[KAALA.uid()] = buildKalavelaUpagraha(sweObjects, KAA, SY, true, weekday, dayBirth, periodStart, portionLength);
        all[MRITYU.uid()] = buildKalavelaUpagraha(sweObjects, MRT, MA, true, weekday, dayBirth, periodStart, portionLength);
        all[ARTHAPRAHAARA.uid()] = buildKalavelaUpagraha(sweObjects, ART, BU, true, weekday, dayBirth, periodStart, portionLength);
        all[YAMAGHANTAKA.uid()] = buildKalavelaUpagraha(sweObjects, YAM, GU, true, weekday, dayBirth, periodStart, portionLength);
        all[GULIKA.uid()] = buildKalavelaUpagraha(sweObjects, GLK, SA, true, weekday, dayBirth, periodStart, portionLength);
        all[MAANDI.uid()] = buildKalavelaUpagraha(sweObjects, MND, SA, false, weekday, dayBirth, periodStart, portionLength);
    }

    protected IUpagrahaEntity buildKalavelaUpagraha(final ISweObjects sweObjects, final IUpagraha upagraha,
            final int rulingGrahaUid, final boolean atMiddleOfPart, final int weekday, final boolean dayBirth,
            final double periodStart, final double portionLength) {

        final int part = calcKalavelaPart(rulingGrahaUid, weekday, dayBirth);

        final double targetJd = periodStart + (part + (atMiddleOfPart ? d05 : d0)) * portionLength;
        final double longitude = fix360(calcAscendant(sweObjects, targetJd));

        return new UpagrahaEntity(upagraha, sweObjects, longitude);
    }

    /**
     * Which of the 7 lorded parts (0-indexed, 0..6) of the day or night belongs to the graha
     * identified by {@code rulingGrahaUid} ({@link ISweObjects}'s SY..SA, 1..7), for a birth on
     * the given civil weekday (0..6, Sunday..Saturday). Pure arithmetic, independently
     * unit-tested against BPHS's own two worked examples and the standard published
     * Gulika-Kalam/Yamagandam tables - see {@code UpagrahaKalavelaTest}.
     */
    static int calcKalavelaPart(final int rulingGrahaUid, final int weekday, final boolean dayBirth) {
        final int shift = dayBirth ? i0 : 4;
        return modulo(i7, rulingGrahaUid - i1 - weekday - shift);
    }

    protected double calcAscendant(final ISweObjects sweObjects, final double jd) {
        final ISweGeoLocation geo = sweObjects.sweLocation();
        final double[] cusps = new double[13];
        final double[] ascmc = new double[10];

        final int result = sweObjects.swissEph().swe_houses_ex(jd, sweObjects.sweOptions().houseFlags(),
                geo.latitude(), geo.longitude(), sweObjects.sweOptions().houseSystem().fid(), cusps, ascmc);

        if (result == ERR) throw new SweRuntimeException("swe_houses_ex failed for a Kalavela upagraha");
        return ascmc[SE_ASC];
    }

    /**
     * The most recent sunrise/sunset (per rsmi) at or before beforeUt. {@code swe_rise_trans}
     * only searches forward, so this steps the search start back a day and, in the rare case
     * the day/night that far back was unusually long, one more day - the same defensive pattern
     * {@link org.jyotisa.app.KundaliFields#calcSunRiseSet()} uses for the same reason.
     */
    protected double findLastRiseOrSetAtOrBefore(final ISweObjects sweObjects, final int rsmi, final double beforeUt) {
        double found = findRiseOrSet(sweObjects, rsmi, beforeUt - d1);
        if (found > beforeUt) found = findRiseOrSet(sweObjects, rsmi, beforeUt - d2);
        return found;
    }

    protected double findRiseOrSet(final ISweObjects sweObjects, final int rsmi, final double searchFromUt) {
        final StringBuilder serr = new StringBuilder(0);
        final DblObj dblobj = new DblObj();
        final ISweGeoLocation geo = sweObjects.sweLocation();
        final ISwissEph swissEph = sweObjects.swissEph();
        final int flags = rsmi | sweObjects.sweOptions().riseSetFlags();

        final int result = swissEph.swe_rise_trans(searchFromUt, SE_SUN, null, SEFLG_SWIEPH, flags,
                geo.coordinates(), geo.pressure(), geo.temperature(), dblobj, serr);

        if (result < 0) throw new SweRuntimeException(serr.toString());
        return dblobj.val;
    }

    @Override
    public IUpagrahaEntity dhuma() {
        return all[DHUMA.uid()];
    }

    @Override
    public IUpagrahaEntity vyatipaata() {
        return all[VYATIPAATA.uid()];
    }

    @Override
    public IUpagrahaEntity parivesha() {
        return all[PARIVESHA.uid()];
    }

    @Override
    public IUpagrahaEntity indrachaapa() {
        return all[INDRACHAAPA.uid()];
    }

    @Override
    public IUpagrahaEntity upaketu() {
        return all[UPAKETU.uid()];
    }

    @Override
    public IUpagrahaEntity kaala() {
        return all[KAALA.uid()];
    }

    @Override
    public IUpagrahaEntity mrityu() {
        return all[MRITYU.uid()];
    }

    @Override
    public IUpagrahaEntity arthaprahaara() {
        return all[ARTHAPRAHAARA.uid()];
    }

    @Override
    public IUpagrahaEntity yamaghantaka() {
        return all[YAMAGHANTAKA.uid()];
    }

    @Override
    public IUpagrahaEntity gulika() {
        return all[GULIKA.uid()];
    }

    @Override
    public IUpagrahaEntity maandi() {
        return all[MAANDI.uid()];
    }

    @Override
    public IUpagrahaEntity[] all() {
        return all;
    }
}
