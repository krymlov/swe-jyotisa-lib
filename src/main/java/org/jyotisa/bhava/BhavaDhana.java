/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaDhana;

/**
 * 2.  Dhana - Wealth, Speech, Family, Savings house
 * <p>
 * Property and financial accumulation, food, language, benefits, securities,
 * family life, donations, education.
 * <p>
 * Signifikators:
 * - Guru - the ability to make money
 * - Buddha - childhood, language, education
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaDhana implements IBhavaDhana {
    B2,
    DAN;

    @Override
    public IBhavaDhana[] all() {
        return values();
    }
}
