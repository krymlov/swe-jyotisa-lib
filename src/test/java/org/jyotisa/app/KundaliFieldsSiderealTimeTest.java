/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.app;

import org.jyotisa.AbstractTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweGeoLocation;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.swisseph.app.SweObjectsOptions.TRUECITRA_AYANAMSA_TRUE_NODE;
import static swisseph.SweConst.SE_ARMC;

/**
 * Local sidereal time, pinned against a quantity computed by a completely different Swiss
 * Ephemeris entry point: {@code swe_houses_ex}'s <b>ARMC</b> is the right ascension of the MC,
 * i.e. local apparent sidereal time expressed in degrees. {@code ARMC / 15} must therefore equal
 * {@link org.jyotisa.api.IKundaliFields#siderealTime()} to within rounding, at any longitude.
 * <p>
 * This is deliberately not a restatement of the production formula. It caught a real defect:
 * the old implementation computed {@code swe_sidtime(julianDay + longitude / 360)}, folding the
 * longitude into the <b>instant</b> rather than adding it as an <b>angle</b>. Because sidereal
 * time runs 1.0027379x faster than solar time, that over-rotates by
 * {@code (longitude / 15) * 0.0027379} hours - at the project's own reference longitude
 * (81&deg;08'E) a full <b>53 seconds</b>, which is exactly the gap a user reported between this
 * library and Jagannatha Hora. Being proportional to longitude, it vanishes at Greenwich, so a
 * test fixture near 0&deg; would not have caught it - hence the spread of longitudes below.
 */
class KundaliFieldsSiderealTimeTest extends AbstractTest {

    private static final double ARCSEC_IN_HOURS = 1.0 / 3600.0 / 15.0;

    private static ISweObjects chartAt(final double longitude, final double latitude) {
        final ISweGeoLocation place = new SweGeoLocation(longitude, latitude, 0);
        return new SweObjects(getSwephExp(),
                new SweJulianDate(new int[]{1970, 4, 4, 12, 20, 40}, 0f, 12.0 + 20 / 60. + 40 / 3600.),
                place, TRUECITRA_AYANAMSA_TRUE_NODE).completeBuild();
    }

    /**
     * ARMC is in degrees of local apparent sidereal time; dividing by 15 gives hours. Compared to
     * a tenth of an arcsecond of hour angle - far tighter than the 53-second defect, but loose
     * enough for the two different code paths' rounding.
     */
    @ParameterizedTest(name = "longitude {0}")
    @ValueSource(doubles = {-150.0, -75.5, -0.001, 0.0, 30.5167, 81.133333, 120.0, 179.9})
    void siderealTimeEqualsArmcOverFifteen(double longitude) {
        final ISweObjects objects = chartAt(longitude, 16.166667);
        final double expected = objects.ascmc()[SE_ARMC] / 15.0;

        assertEquals(expected, new Kundali(KUNDALI_7_KARAKAS, objects).fields().siderealTime(),
                0.1 * ARCSEC_IN_HOURS, "local sidereal time at longitude " + longitude);
    }

    /**
     * The reference chart the whole {@code refcharts} suite is built on, against the value
     * {@code swetest} prints for it ({@code ARMC 98.7031864} -> {@code 6:34:48.76}) and that
     * Jagannatha Hora displays as {@code 6:34:49}.
     */
    @Test
    void machilipatnam1970_matchesSwetestAndJagannathaHora() {
        final double armcDegrees = 98.7031864;
        assertEquals(armcDegrees / 15.0,
                new Kundali(KUNDALI_7_KARAKAS, chartAt(81.133333, 16.166667)).fields().siderealTime(),
                0.5 * ARCSEC_IN_HOURS, "sidereal time of the 1970 reference chart");
    }

    /** Sidereal time is an hour angle: it must stay inside [0, 24) whatever the longitude. */
    @ParameterizedTest(name = "longitude {0} stays in range")
    @ValueSource(doubles = {-179.9, -90.0, 0.0, 90.0, 179.9})
    void siderealTimeStaysWithinTwentyFourHours(double longitude) {
        final double st = new Kundali(KUNDALI_7_KARAKAS, chartAt(longitude, 16.166667))
                .fields().siderealTime();
        assertTrue(st >= 0.0 && st < 24.0,
                "sidereal time out of range at longitude " + longitude + ": " + st);
    }
}
