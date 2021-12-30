/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaParigha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaParigha implements INityaYogaParigha {
    NY19,
    PARI;

    @Override
    public INityaYogaParigha[] all() {
        return values();
    }
}
