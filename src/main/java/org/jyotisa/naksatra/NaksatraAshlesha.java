/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.naksatra.INaksatraAshlesha;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 9. Ashlesha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraAshlesha implements INaksatraAshlesha {
    N9,
    ASL;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public INaksatraAshlesha[] all() {
        return values();
    }
}
