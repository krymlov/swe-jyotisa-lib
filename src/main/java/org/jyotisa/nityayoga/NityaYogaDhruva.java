/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaDhruva;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaDhruva implements INityaYogaDhruva {
    NY12,
    DHRU;

    @Override
    public INityaYogaDhruva[] all() {
        return values();
    }
}
