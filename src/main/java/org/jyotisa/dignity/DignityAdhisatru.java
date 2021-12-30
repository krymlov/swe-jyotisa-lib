/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityAdhisatru;

/**
 * 3 Adhisatru
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityAdhisatru implements IDignityAdhisatru {
    DG3,
    ADS;

    @Override
    public IDignityAdhisatru[] all() {
        return values();
    }
}
