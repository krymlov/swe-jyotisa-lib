/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD1;

/**
 * 1 Rasi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD1 implements IVargaD1 {
    D1;

    @Override
    public IVargaD1[] all() {
        return values();
    }
}
