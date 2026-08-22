/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.avastha;

import org.jyotisa.api.avastha.IAvasthaVriddha;

/**
 * 4.  Vriddha - the elder - waning
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum AvasthaVriddha implements IAvasthaVriddha {
    AV4,
    VRIDDHA;

    @Override
    public IAvasthaVriddha[] all() {
        return values();
    }
}
