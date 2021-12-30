/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityNeecha;

/**
 * 1 Neecha - Debilitation
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityNeecha implements IDignityNeecha {
    DG1,
    NEE;

    @Override
    public IDignityNeecha[] all() {
        return values();
    }
}
