/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaAyur;

/**
 * 8. Mrityu - Ayur, Death (Moksha), (Mrityu or Randhra) bhava
 * <p>
 * The unknown, transcendent, exploring the unknown, anything unusual, transformation,
 * length of the life, mysterious events, accidents, chronic illness, intuition, instincts,
 * scandals, shame, frustration, inability to forward  myself, unearned wealth, heritage,
 * excitement, vitality, sexual energy, fraud, abuse, death, everything associated with the end of life,
 * longevity, failure, suicide, violent deaths, secret life, pension, loans, gifts.
 * <p>
 * Signifikator:
 * - Shani - Death and longevity
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaAyur implements IBhavaAyur {
    B8,
    AYR;

    @Override
    public IBhavaAyur[] all() {
        return values();
    }
}
