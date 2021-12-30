/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaJanma;

/**
 * 0. Lagna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaJanma implements ILagnaJanma {
    L0,
    JL;

    @Override
    public ILagnaJanma[] all() {
        return values();
    }
}
