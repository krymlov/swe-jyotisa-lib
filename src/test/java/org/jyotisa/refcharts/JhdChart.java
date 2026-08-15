/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refcharts;

import org.swisseph.api.ISweGeoLocation;
import org.swisseph.api.ISweJulianDate;
import org.swisseph.app.SweGeoLocation;
import org.swisseph.app.SweJulianDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader for a Jagannatha Hora <code>.jhd</code> birth data file - the same format
 * {@code swe-java-lib}'s own {@code org.swisseph.JhdChart} reads, carried here because that
 * one lives in a test source tree and is not published in any jar.
 * <p>
 * One value per line:
 * <pre>
 *  1  month
 *  2  day
 *  3  year
 *  4  local time, <b>degrees.minutes style</b> (17.5066666 is 17:50:40)
 *  5  time zone, hours, <b>sign reversed</b> (-5.30 means UTC+5:30)
 *  6  longitude, <b>sign reversed</b>, degrees.minutes (-81.08 means 81 deg 08 min East)
 *  7  latitude, degrees.minutes (16.10 means 16 deg 10 min North)
 *  8  altitude, meters
 * 13  city
 * 14  country
 * </pre>
 * Longitude, latitude, time zone and the local time are <b>all four</b> stored as
 * <b>degrees.minutes</b>, not decimal: 16.10 is 16&deg;10', i.e. 16.1666...
 * <p>
 * That the <i>time</i> and <i>time zone</i> fields follow the same convention as the
 * coordinates is worth proving, because reading them as decimal hours is the obvious
 * mistake and it is silently wrong by minutes. Three of Jagannatha Hora's own shipped
 * charts settle it, each against independently known data:
 * <pre>
 * Gandhi       lon 69.49 -&gt; 69&deg;49'E, tz 4.392667 -&gt; 4&deg;39'16"   69.8167/15 = 4h39m16s exactly
 * Vivekananda  lon 88.30 -&gt; 88&deg;30'E, tz 5.54     -&gt; 5&deg;54'      88.5/15    = 5h54m    exactly
 *              time 6.3300 -&gt; 06:33, his documented birth time
 * India        tz 5.30 -&gt; 5:30 (decimal would be 5:18), 77.13/28.40 -&gt; Delhi 77&deg;13'E 28&deg;40'N
 * </pre>
 * Both time zones are the local mean time of that same longitude to the second, which only
 * works out under the degrees.minutes reading.
 * <p>
 * <b>{@code swe-java-lib}'s own {@code org.swisseph.JhdChart} differs here</b> - it reads
 * {@code localTime} and {@code timeZone} as plain decimal. See this workspace's CLAUDE.md.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public final class JhdChart {

    private final int month, day, year;
    private final double localTime;
    private final float timeZone;
    private final double longitude, latitude, altitude;
    private final String city, country;

    private JhdChart(List<String> lines) {
        this.month = (int) Double.parseDouble(lines.get(0));
        this.day = (int) Double.parseDouble(lines.get(1));
        this.year = (int) Double.parseDouble(lines.get(2));
        this.localTime = degreesMinutes(Double.parseDouble(lines.get(3)));
        this.timeZone = (float) degreesMinutes(-Double.parseDouble(lines.get(4)));
        this.longitude = degreesMinutes(-Double.parseDouble(lines.get(5)));
        this.latitude = degreesMinutes(Double.parseDouble(lines.get(6)));
        this.altitude = Double.parseDouble(lines.get(7));
        this.city = lines.size() > 12 ? lines.get(12) : "";
        this.country = lines.size() > 13 ? lines.get(13) : "";
    }

    /**
     * @param value degrees with the minutes in the fractional part, e.g. 16.10
     * @return decimal degrees, e.g. 16.16666...
     */
    static double degreesMinutes(double value) {
        final double sign = Math.signum(value);
        final double abs = Math.abs(value);
        final double deg = Math.floor(abs);
        final double min = (abs - deg) * 100.;
        return sign * (deg + min / 60.);
    }

    public static JhdChart read(String resource) {
        try (InputStream in = JhdChart.class.getClassLoader().getResourceAsStream(resource)) {
            if (null == in) throw new IllegalArgumentException("resource not found: " + resource);
            final List<String> lines = new ArrayList<>();
            try (BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                for (String l = r.readLine(); null != l; l = r.readLine()) lines.add(l.trim());
            }
            return new JhdChart(lines);
        } catch (IOException e) {
            throw new IllegalStateException("cannot read " + resource, e);
        }
    }

    /** local date as {year, month, day, hours, minutes} */
    public int[] date() {
        final double[] hms = ISweJulianDate.splitTime(localTime);
        return new int[]{year, month, day, (int) hms[0], (int) hms[1]};
    }

    public ISweJulianDate julianDate() {
        return new SweJulianDate(date(), timeZone, localTime);
    }

    public ISweGeoLocation geoLocation() {
        return new SweGeoLocation(longitude, latitude, altitude);
    }

    /** the UT of this local time, as swetest wants it: {@code -ut<hh:mm:ss>} */
    public String utcTime() {
        final double ut = localTime - timeZone;
        final double[] hms = ISweJulianDate.splitTime(ut);
        return String.format("%02d:%02d:%02d", (int) hms[0], (int) hms[1], (int) Math.round(hms[2]));
    }

    public int year() { return year; }
    public int month() { return month; }
    public int day() { return day; }
    public double localTime() { return localTime; }
    public float timeZone() { return timeZone; }
    public double longitude() { return longitude; }
    public double latitude() { return latitude; }
    public double altitude() { return altitude; }
    public String city() { return city; }
    public String country() { return country; }

    @Override
    public String toString() {
        return city + ", " + country + " " + year + "-" + month + "-" + day
                + " " + localTime + "h tz=" + timeZone + " " + longitude + "E " + latitude + "N";
    }
}
