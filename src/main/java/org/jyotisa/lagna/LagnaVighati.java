/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaVighati;

/**
 * 4. Vighati
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaVighati implements ILagnaVighati {
    L4,
    VG;

    @Override
    public ILagnaVighati[] all() {
        return values();
    }
}
