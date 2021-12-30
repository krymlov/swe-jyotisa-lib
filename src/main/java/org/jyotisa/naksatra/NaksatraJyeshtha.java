/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.naksatra;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.naksatra.INaksatraJyeshtha;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 18. Jyeshtha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NaksatraJyeshtha implements INaksatraJyeshtha {
    N18,
    JYE;

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }

    @Override
    public INaksatraJyeshtha[] all() {
        return values();
    }
}
