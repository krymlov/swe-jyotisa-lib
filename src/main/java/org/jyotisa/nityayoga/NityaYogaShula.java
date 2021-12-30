/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaShula;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaShula implements INityaYogaShula {
    NY9,
    SULA;

    @Override
    public INityaYogaShula[] all() {
        return values();
    }
}
