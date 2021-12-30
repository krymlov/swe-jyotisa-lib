/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignitySama;

/**
 * 5 Sama
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignitySama implements IDignitySama {
    DG5,
    SAM;

    @Override
    public IDignitySama[] all() {
        return values();
    }
}
