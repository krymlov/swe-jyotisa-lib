/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaShubha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaShubha implements INityaYogaShubha {
    NY23,
    SUBH;

    @Override
    public INityaYogaShubha[] all() {
        return values();
    }
}
