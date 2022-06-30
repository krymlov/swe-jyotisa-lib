/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-05
 */

package org.jyotisa.graha.syama;


import org.jyotisa.api.graha.IGrahaSyama;

/**
 * <pre>
 * Neptune or Syama (Bluish White planet)
 *
 * Shukrahah Prosthapade Poorve Samaruhya Virochate Uttare tu Parikramya Sahitah Samudikshyate….[15-Bheeshma.3]
 * Syamograhah Prajwalitah Sadhooma iva Pavakah Aaindram Tejaswi Naksha- tram Jyesthaam Aakramya
 * Tishthati…[16-Bheeshma.3]
 *
 * Vyas mentions that a bluish white (Syama) planet was in Jyeshtha and it was smoky (Sadhoom).
 * Neelkantha calls it “Parigha” (circumference) in his commentary on Mahabharat.
 * He could mean that its orbit was almost of the circumference of our solar system.
 *
 * How did Sage Vyas see color of these planets ?
 * Mirrors and Microscopic Vision are mentioned in Mahabharata (Shanti A. 15,308).
 * So, lenses and telescopes must also be present at that time.
 * In ancient literature, Durbini (device used to see objects at far off distance, similar to binoculars) were
 * mentioned.
 * </pre>
 *
 * @author Yura Krymlov
 * @version 1.0, 2020-05
 */
public enum GrahaSyama implements IGrahaSyama {
    G11, SM, SYAMA, Ne, Neptune;

    @Override
    public IGrahaSyama[] all() {
        return values();
    }
}
