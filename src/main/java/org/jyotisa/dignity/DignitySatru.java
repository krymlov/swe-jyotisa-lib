/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignitySatru;

/**
 * 4 Satru
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignitySatru implements IDignitySatru {
    DG4,
    SRU;

    @Override
    public IDignitySatru[] all() {
        return values();
    }
}
