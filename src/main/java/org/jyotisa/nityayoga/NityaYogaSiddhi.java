/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaSiddhi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaSiddhi implements INityaYogaSiddhi {
    NY16,
    SDHI;

    @Override
    public INityaYogaSiddhi[] all() {
        return values();
    }
}
