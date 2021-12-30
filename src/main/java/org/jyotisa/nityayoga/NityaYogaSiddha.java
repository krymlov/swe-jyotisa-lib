/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaSiddha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaSiddha implements INityaYogaSiddha {
    NY21,
    SDHA;

    @Override
    public INityaYogaSiddha[] all() {
        return values();
    }
}
