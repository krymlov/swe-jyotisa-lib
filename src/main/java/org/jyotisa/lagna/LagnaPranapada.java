/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaPranapada;

/**
 * 7. Pranapada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaPranapada implements ILagnaPranapada {
    L7,
    PP;

    @Override
    public ILagnaPranapada[] all() {
        return values();
    }
}
