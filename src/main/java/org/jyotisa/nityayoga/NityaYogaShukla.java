/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaShukla;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaShukla implements INityaYogaShukla {
    NY24,
    SUKL;

    @Override
    public INityaYogaShukla[] all() {
        return values();
    }
}
