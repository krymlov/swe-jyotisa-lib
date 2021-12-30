/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVyaghata;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVyaghata implements INityaYogaVyaghata {
    NY13,
    VYAG;

    @Override
    public INityaYogaVyaghata[] all() {
        return values();
    }
}
