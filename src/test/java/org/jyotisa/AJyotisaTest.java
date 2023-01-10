/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-01
 */

package org.jyotisa;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.jyotisa.api.IKundali;
import org.jyotisa.app.Kundali;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.app.SweGeoLocation;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;
import swisseph.SwissEph;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.longBitsToDouble;
import static java.util.TimeZone.getTimeZone;
import static org.jyotisa.app.KundaliOptions.KUNDALI_8_KARAKAS;
import static org.swisseph.api.ISweConstants.EPHE_PATH;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public abstract class AJyotisaTest {
    protected static final Random RANDOM = new Random();
    protected static final ThreadLocal<ISwissEph> SWISS_EPHS = new ThreadLocal<>();
    protected static final ThreadLocal<ISwissEph> SWEPH_EXPS = new ThreadLocal<>();

    protected Calendar newCalendar(TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    protected IKundali newChennaiKundali(ISwissEph swissEph) {
        return new Kundali(KUNDALI_8_KARAKAS, new SweObjects(swissEph, new SweJulianDate(
                newCalendar(getTimeZone("Asia/Calcutta"))), GEO_CHENNAI, TRUECITRA_AYANAMSA).completeBuild());
    }

    /**
     * Place : Chennai, Tamil Nadu, India<br>
     * Location : 13.09, 80.28 Time Zone : IST (+05:30)
     */
    public static final ISweGeoLocation GEO_CHENNAI = new SweGeoLocation(80 + (16 / 60.), 13 + (5 / 60.), 6.7);


    protected IKundali newKyivKundali(ISwissEph swissEph) {
        return new Kundali(KUNDALI_8_KARAKAS, new SweObjects(swissEph, new SweJulianDate(
                newCalendar(getTimeZone("Europe/Kiev"))), GEO_KYIV, TRUECITRA_AYANAMSA).completeBuild());
    }

    /**
     * Place : Kyiv, Ukraine<br>
     * Location : 50°27'N, 30°31'E. Time Zone : (+02:00)
     */
    public static final ISweGeoLocation GEO_KYIV = new SweGeoLocation(30 + (31 / 60.), 50 + (26 / 60.), 180);

    /**
     * @param origin the least value, unless greater than bound
     * @param bound  the upper bound (exclusive), must not equal origin
     * @return a pseudorandom value
     */
    protected static double nextDouble(double origin, double bound) {
        double r = RANDOM.nextDouble();

        if (origin < bound) {
            r = r * (bound - origin) + origin;
            if (r >= bound) // correct for rounding
                r = longBitsToDouble(doubleToLongBits(bound) - 1);
        }

        return r;
    }

    protected static SwissEph newSwissEph(String ephePath) {
        return new SwissEph(ephePath);
    }

    protected static SwissEph newSwissEph() {
        return new SwissEph(EPHE_PATH);
    }

    protected static SwephNative newSwephExp(String ephePath) {
        return new SwephNative(ephePath);
    }

    protected static SwephNative newSwephExp() {
        return new SwephNative(EPHE_PATH);
    }

    public static ISwissEph getSwissEph() {
        ISwissEph swissEph = SWISS_EPHS.get();

        if (null == swissEph) {
            swissEph = newSwissEph();
            SWISS_EPHS.set(swissEph);
        }

        return swissEph;
    }

    public static ISwissEph getSwephExp() {
        ISwissEph swissEph = SWEPH_EXPS.get();

        if (null == swissEph) {
            swissEph = newSwephExp();
            SWEPH_EXPS.set(swissEph);
        }

        return swissEph;
    }

    public static void closeSwissEph() {
        try (ISwissEph swissEph = SWISS_EPHS.get()) {
            if (null == swissEph) return;
            SWISS_EPHS.remove();
        }
    }

    public static void closeSwephExp() {
        try (ISwissEph swissEph = SWEPH_EXPS.get()) {
            if (null == swissEph) return;
            SWEPH_EXPS.remove();
        }
    }

    @BeforeAll
    protected void callBeforeAll() {
    }

    @BeforeEach
    protected void callBeforeEach() {
    }

    @AfterEach
    protected void callAfterEach() {
        closeSwissEph();
        closeSwephExp();
    }

    @AfterAll
    protected void callAfterAll() {
    }

}
