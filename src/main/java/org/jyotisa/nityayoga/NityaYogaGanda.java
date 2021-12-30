/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaGanda;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaGanda implements INityaYogaGanda {
    NY10,
    GAND;

    @Override
    public INityaYogaGanda[] all() {
        return values();
    }
}
