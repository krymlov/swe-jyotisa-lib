/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaGhati;

/**
 * 3. Ghati
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaGhati implements ILagnaGhati {
    L3,
    GL;

    @Override
    public ILagnaGhati[] all() {
        return values();
    }
}
