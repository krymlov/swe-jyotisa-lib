/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaVyatipata;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaVyatipata implements INityaYogaVyatipata {
    NY17,
    VYAT;

    @Override
    public INityaYogaVyatipata[] all() {
        return values();
    }
}
