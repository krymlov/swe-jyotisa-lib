/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVajra;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVajra implements INityaYogaVajra {
    NY15,
    VAJR;

    @Override
    public INityaYogaVajra[] all() {
        return values();
    }
}
