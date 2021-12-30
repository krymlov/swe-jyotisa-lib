/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaBrahma;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaBrahma implements INityaYogaBrahma {
    NY25,
    BRAH;

    @Override
    public INityaYogaBrahma[] all() {
        return values();
    }
}
