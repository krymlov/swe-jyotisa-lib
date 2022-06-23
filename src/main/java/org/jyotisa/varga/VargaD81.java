/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD81;

/**
 * 81 NavaNavāṁśa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD81 implements IVargaD81 {
    D81;

    @Override
    public IVargaD81[] all() {
        return values();
    }
}
