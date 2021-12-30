/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaLabha;

/**
 * 11. Labha - Gains, Profits, Greed
 * <p>
 * Earnings, income, pleasant options, cash turnover, friends, allies, winnings,
 * welfare, goals, ambitions, the image, business.
 * <p>
 * Signifikator:
 * - Guru - earnings
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaLabha implements IBhavaLabha {
    B11,
    AYA;

    @Override
    public IBhavaLabha[] all() {
        return values();
    }
}
