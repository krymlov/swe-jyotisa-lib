/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.avastha;

import org.jyotisa.api.avastha.IAvasthaKumara;

/**
 * 2.  Kumara - the youth - gathering strength
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum AvasthaKumara implements IAvasthaKumara {
    AV2,
    KUMARA;

    @Override
    public IAvasthaKumara[] all() {
        return values();
    }
}
