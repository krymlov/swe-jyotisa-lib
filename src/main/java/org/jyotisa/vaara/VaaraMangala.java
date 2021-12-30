/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaMangala;
import org.jyotisa.api.vaara.IVaaraMangala;

import static org.jyotisa.graha.mangala.GrahaMangala.MANGALA;

/**
 * 3. Mangalavaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraMangala implements IVaaraMangala {
    VR3,
    MAVR;

    @Override
    public IGrahaMangala lord() {
        return MANGALA;
    }

    @Override
    public IVaaraMangala[] all() {
        return values();
    }
}
