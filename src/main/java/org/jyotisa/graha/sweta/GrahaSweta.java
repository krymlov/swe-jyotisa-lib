/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-05
 */

package org.jyotisa.graha.sweta;


import org.jyotisa.api.graha.IGrahaSweta;

/**
 * <pre>
 * Uranus or Sweta (Greenish White planet)
 *
 * Vishesheena hi Vaarshneya Chitraam Pidayate Grahah….[10-Udyog.143]
 * Swetograhastatha Chitraam Samitikryamya Tishthati….[12-Bheeshma.3]
 *
 * Here Vyas states that some greenish white (Sweta) planet has crossed Chitra Nakshatra.
 * Neelakantha of 17th century also had the knowledge of Uranus or Sweta.
 * Sweta means greenish white, which was later discovered to be the color of Uranus.
 *
 * Neelakantha writes in his commentary on Mahabharat (Udyog 143) that Shveta, or Mahapata(one which has
 * greater orbit) was a famous planet in the Astronomical science of India.
 * Neelakantha calls this “Mahapata” which means having greater orbit and it indicates a planet beyond Saturn.
 * </pre>
 *
 * @author Yura Krymlov
 * @version 1.0, 2020-05
 */
public enum GrahaSweta implements IGrahaSweta {
    G10,
    SW,
    SWETA;

    @Override
    public IGrahaSweta[] all() {
        return values();
    }
}
