/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaDharma;

/**
 * 9. Dharma - Natural Law, Good-fortune,
 * - House of the success (proper actions)
 * - Bhagya (Dharma) bhava
 * <p>
 * Success, higher knowledge, philosophy, gurus, long trips, pilgrimages, happiness,
 * religion, devotional service, meditation, prayer, father, God, happy destiny, proper actions,
 * ethics, law, divine love, higher education institutions, a link with the divine,
 * legislation, public authorities, chiefs, charity, justice, prosperity.
 * <p>
 * Signifikators:
 * - Guru - the proper actions, teachers
 * - Surya - father
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaDharma implements IBhavaDharma {
    B9,
    DRM;

    @Override
    public IBhavaDharma[] all() {
        return values();
    }
}
