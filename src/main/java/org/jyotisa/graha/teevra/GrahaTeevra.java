/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-05
 */

package org.jyotisa.graha.teevra;


import org.jyotisa.api.graha.IGrahaTeevra;

/**
 * <pre>
 * Pluto or Teekshana/Teevra
 *
 * Pluto was discovered to the modern world in 1930.
 *
 * Krittikaam Peedayan Teekshnaihi Nakshatram…[30-Bheeshma.3]
 *
 * Here Vyas states that some immobile liminary troubling Krittika (Pleides) with its sharp rays.
 * This was mentioned as Nakshatra because it was stationary at one place for long period, so it must be a
 * planet in outer orbit.
 *
 * It gets mentioned again as
 *
 * Krittikasu Grahasteevro Nakshatre Prathame Jvalan…… [26- Bhishma.3]
 *
 * Mathematical calculations make it clear that Krittika and Pluto were conjunct during mahabharata period.
 *
 * Vyas has mentioned ‘seven Great planets‘, three times in Mahabharat.
 *
 * Deepyamanascha Sampetuhu Divi Sapta Mahagrahah…[2-Bhishma.17]
 *
 * It means that the seven great planets were brilliant and shining. In traditional indian astrology, Rahu and
 * Ketu are nodes/shadows and do not shine like stars or planets.
 *
 * Nissaranto Vyadrushanta Suryaat Sapta Mahagrahah…[4-Karna 37]
 *
 * This line states that these seven great planets were ‘seen‘ moving away from the Sun.
 * Since Rahu & Ketu cannot be ‘seen’, they can be ruled out.
 * This statement is made on sixteenth day of Kurukshetra War, so the Moon has moved away from Sun.
 * Hence we can assume Mars, Mercury, Jupiter, Venus, Saturn, Uranus and Neptune are the seven great planets
 * mentioned by Vyas.
 * Pluto was neglected due to its lesser impact on earth.
 *
 * </pre>
 *
 * @author Yura Krymlov
 * @version 1.0, 2020-05
 */
public enum GrahaTeevra implements IGrahaTeevra {
    G12,
    TE,
    TEEVRA;

    @Override
    public IGrahaTeevra[] all() {
        return values();
    }
}
