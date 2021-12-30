/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVariyan;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVariyan implements INityaYogaVariyan {
    NY18,
    VARI;

    @Override
    public INityaYogaVariyan[] all() {
        return values();
    }
}
