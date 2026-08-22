/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.avastha;

import org.jyotisa.api.avastha.IAvasthaBala;

/**
 * 1.  Bala - the infant - newly entered, with little of its power yet awake
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum AvasthaBala implements IAvasthaBala {
    AV1,
    BALA;

    @Override
    public IAvasthaBala[] all() {
        return values();
    }
}
