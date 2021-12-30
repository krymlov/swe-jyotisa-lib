/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaIndra;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaIndra implements INityaYogaIndra {
    NY26,
    INDR;

    @Override
    public INityaYogaIndra[] all() {
        return values();
    }
}
