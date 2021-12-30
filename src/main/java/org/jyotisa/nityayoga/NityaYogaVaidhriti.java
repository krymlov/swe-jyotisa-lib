/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVaidhriti;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVaidhriti implements INityaYogaVaidhriti {
    NY27,
    VAID;

    @Override
    public INityaYogaVaidhriti[] all() {
        return values();
    }
}
