/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.lagna;

import org.jyotisa.api.lagna.ILagnaSree;

/**
 * 6. Sree
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum LagnaSree implements ILagnaSree {
    L6,
    SL;

    @Override
    public ILagnaSree[] all() {
        return values();
    }
}
