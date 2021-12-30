/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaPreeti;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaPreeti implements INityaYogaPreeti {
    NY2,
    PREE;

    @Override
    public INityaYogaPreeti[] all() {
        return values();
    }
}
