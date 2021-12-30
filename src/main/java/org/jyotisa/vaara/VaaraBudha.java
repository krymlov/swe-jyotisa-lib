/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.vaara;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.vaara.IVaaraBudha;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 4. Budhavaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VaaraBudha implements IVaaraBudha {
    VR4,
    BUVR;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public IVaaraBudha[] all() {
        return values();
    }
}
