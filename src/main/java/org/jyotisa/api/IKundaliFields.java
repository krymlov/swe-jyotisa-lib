/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2020-01
*/

package org.jyotisa.api;


import java.io.Serializable;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-01
 */
public interface IKundaliFields extends Serializable {
    double moonrise();
    double moonset();

    double sunset();
    double sunrise();
    
    /**
     * @return The time taken by earth to spin one complete rotation of 360 degrees on its axis. Average
     *         duration of one sidereal day is 23 hrs, 56 min, 4.091 sec.
     */
    double siderealTime();

    /**
     * @return The duration of time between one sunrise to another sunrise is a Savana day. For people living
     *         in northern hemisphere, from winter solstice day onwards, the sunshine hours (dinamana)
     *         increases and night hours (ratrimana) decreases. As the sunrise every day is earlier than the
     *         previous day, the duration of the savana day is less than 24 hours till the Sun reaches its
     *         maximum declination at summer solstice. After that the dinamana reduces and the ratrimana
     *         increases. Since the sunrise of every day is later than the previous day, the duration of the
     *         savana day is more than 24 hours till it reaches the winter solstice again.
     */
    double savanaDay();

    /**
     * @return Day-time (Time between sunrise and sunset)
     */
    double dinamana();

    /**
     * @return Night-time (Time between sunset and sunrise)
     */
    double ratrimana();

    double mishramaanKaala();

    /**
     * Ishtakala is the time elapsed since the time of sunrise to the time of birth.
     * @return the duration of time elapsed from the time of sunrise
     */
    double ishtaKaala();

    /**
     * @return Surya position (spashta)
     */
    double suryaSpashta();

    double bhavaLagna();
    double ghatiLagna();
    double horaLagna();
}
