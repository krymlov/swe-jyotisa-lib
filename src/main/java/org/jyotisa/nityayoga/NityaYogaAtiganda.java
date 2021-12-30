/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaAtiganda;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaAtiganda implements INityaYogaAtiganda {
    NY6,
    ATIG;

    @Override
    public INityaYogaAtiganda[] all() {
        return values();
    }
}
