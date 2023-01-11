/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-01
 */

package org.jyotisa;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaEnum;
import org.jyotisa.app.Kundali;
import org.jyotisa.varga.EVarga;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.app.SweGeoLocation;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;
import swisseph.SwissEph;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.util.TimeZone;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.longBitsToDouble;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.TimeZone.getTimeZone;
import static org.apache.commons.io.FilenameUtils.getName;
import static org.apache.commons.io.FilenameUtils.getPath;
import static org.jyotisa.app.KundaliOptions.KUNDALI_8_KARAKAS;
import static org.swisseph.api.ISweConstants.EPHE_PATH;
import static org.swisseph.api.ISweConstants.UTF8;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA;
import static org.swisseph.utils.IDegreeUtils.toDMS;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public abstract class AbstractTest {
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

    public static final ISweGeoLocation GEO_LUCKNOW = new SweGeoLocation(81.83, 25.95, 123);

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

    public static StringBuilder printKundali(IKundali kundali) {
        StringBuilder builder = new StringBuilder(kundali.toString());
        builder.append('\n');

        final Iterator<IVargaEnum> iterator = EVarga.iterator();
        final IGrahaEntity[] grahas = kundali.grahas().all();

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            builder.append(varga.code()).append("\t= ");

            for (int i = 0; i < grahas.length; i++) {
                final IGrahaEntity graha = grahas[i];
                final double longitude = graha.longitude();
                final IRasi rasi = varga.rasi(longitude);
                final double vrl = varga.rasiLongitude(longitude);

                builder.append(' ').append(rasi.following());
                builder.append('[').append(toDMS(vrl)).append(']');
            }

            builder.append('\n');
        }

        return builder;
    }

    protected String loadAndAssert(String resourceName, String content) throws IOException {
        final URL resourceUrl = getClass().getResource(resourceName);

        String reference = null;
        if ( null != resourceUrl && new File(resourceUrl.getFile()).exists() ) {
            reference = IOUtils.toString(resourceUrl, UTF_8).trim();
        }

        content = saveResourceInTempDirectory(resourceName, content); // for faster comparison and fixes
        Assertions.assertNotNull(reference);

        if (!reference.equals(content)) Assertions.fail();
        return reference;
    }

    protected String saveResourceInTempDirectory(String resourceName, String content) throws IOException {
        final URL resourceUrl = getClass().getResource(resourceName);
        String resFile = resourceName;

        if ( null != resourceUrl) {
            resFile = FilenameUtils.normalize(resourceUrl.getFile());
            if (File.separatorChar == '\\') resFile = resFile.replaceFirst("\\\\", "");
        }

        File tempFilePath = new File(FileUtils.getTempDirectory(), getPath(resFile));
        File tempFileName = new File(tempFilePath, getName(resFile));
        FileUtils.write(tempFileName, content = content.trim(), UTF8);
        return content;
    }
}
