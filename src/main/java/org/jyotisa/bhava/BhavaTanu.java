/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaTanu;

/**
 * 1. Tanu - Ascendant, Self, Body house
 * <p>
 * The physical "I", personal character, viability, main tendencies, general wel-being,
 * temperament, health, main orientation of life, fame, success
 * <p>
 * Signifikator: Surya - "I"
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaTanu implements IBhavaTanu {
    B1,
    TAN;

    @Override
    public IBhavaTanu[] all() {
        return values();
    }

}
