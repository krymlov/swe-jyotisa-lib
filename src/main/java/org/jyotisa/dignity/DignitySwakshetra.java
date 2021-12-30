/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignitySwakshetra;

/**
 * 8 Swakshetra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignitySwakshetra implements IDignitySwakshetra {
    DG8,
    SWT;

    @Override
    public IDignitySwakshetra[] all() {
        return values();
    }
}
