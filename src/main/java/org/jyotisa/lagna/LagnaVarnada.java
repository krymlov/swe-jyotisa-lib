/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaVarnada;

/**
 * 5. Varnada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaVarnada implements ILagnaVarnada {
    L5,
    VL;

    @Override
    public ILagnaVarnada[] all() {
        return values();
    }
}
