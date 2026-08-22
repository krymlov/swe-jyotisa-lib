/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.avastha;

import org.jyotisa.api.avastha.IAvasthaYuva;

/**
 * 3.  Yuva - the adult - at full power, the strongest of the five
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum AvasthaYuva implements IAvasthaYuva {
    AV3,
    YUVA;

    @Override
    public IAvasthaYuva[] all() {
        return values();
    }
}
