/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaHora;

/**
 * 2. Hora
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaHora implements ILagnaHora {
    L2,
    HL;

    @Override
    public ILagnaHora[] all() {
        return values();
    }
}
