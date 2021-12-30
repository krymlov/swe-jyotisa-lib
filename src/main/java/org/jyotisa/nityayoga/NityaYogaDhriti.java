/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaDhriti;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaDhriti implements INityaYogaDhriti {
    NY8,
    DHRI;

    @Override
    public INityaYogaDhriti[] all() {
        return values();
    }
}
