/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaKarma;

/**
 * 10. Karma - Actions, Occupation
 * <p>
 * Career, status, purpose in life, profession, popularity, reputation, ambition, authority
 * <p>
 * Signifikators:
 * - Buddha -  profession, commerce
 * - Guru -  profession
 * - Surya - fame, prestige
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaKarma implements IBhavaKarma {
    B10,
    KRM;

    @Override
    public IBhavaKarma[] all() {
        return values();
    }
}
