/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVriddhi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVriddhi implements INityaYogaVriddhi {
    NY11,
    VRID;

    @Override
    public INityaYogaVriddhi[] all() {
        return values();
    }
}
