/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaIndu;

/**
 * 8. Indu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaIndu implements ILagnaIndu {
    L8,
    IL;

    @Override
    public ILagnaIndu[] all() {
        return values();
    }
}
