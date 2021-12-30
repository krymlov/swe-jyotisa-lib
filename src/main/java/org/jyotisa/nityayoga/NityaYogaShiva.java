/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaShiva;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaShiva implements INityaYogaShiva {
    NY20,
    SIVA;

    @Override
    public INityaYogaShiva[] all() {
        return values();
    }
}
