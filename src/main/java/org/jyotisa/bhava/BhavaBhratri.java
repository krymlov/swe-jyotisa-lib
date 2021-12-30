/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaBhratri;

/**
 * 3.  Bhratri - Siblings, Intentions, Sahaja bhava
 * <p>
 * Decisiveness, courage, younger brothers and sisters, neighbors, determination,
 * the ability to realize expectations, intellectual ability, memory, strength, short trips,
 * leadership, communication, commerce, self-expression.
 * <p>
 * Signifikator:
 * - Mangala - energy and courage
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaBhratri implements IBhavaBhratri {
    B3,
    BRT;

    @Override
    public IBhavaBhratri[] all() {
        return values();
    }
}
