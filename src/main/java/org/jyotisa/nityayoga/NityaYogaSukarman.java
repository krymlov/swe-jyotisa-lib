/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaSukarman;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaSukarman implements INityaYogaSukarman {
    NY7,
    SUKA;

    @Override
    public INityaYogaSukarman[] all() {
        return values();
    }
}
