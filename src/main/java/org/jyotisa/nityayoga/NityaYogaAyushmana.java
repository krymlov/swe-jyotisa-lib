/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaAyushmana;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaAyushmana implements INityaYogaAyushmana {
    NY3,
    AYUS;

    @Override
    public INityaYogaAyushmana[] all() {
        return values();
    }
}
