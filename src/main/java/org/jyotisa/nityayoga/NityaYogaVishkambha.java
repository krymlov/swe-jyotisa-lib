/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVishkambha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVishkambha implements INityaYogaVishkambha {
    NY1,
    VISK;

    @Override
    public INityaYogaVishkambha[] all() {
        return values();
    }
}
