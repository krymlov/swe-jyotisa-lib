/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaSobhana;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaSobhana implements INityaYogaSobhana {
    NY5,
    SOBH;

    @Override
    public INityaYogaSobhana[] all() {
        return values();
    }
}
