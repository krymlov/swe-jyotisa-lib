/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaHarshana;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaHarshana implements INityaYogaHarshana {
    NY14,
    HARS;

    @Override
    public INityaYogaHarshana[] all() {
        return values();
    }
}
