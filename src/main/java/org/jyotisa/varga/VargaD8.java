/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD8;

/**
 * 8 Ashtamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD8 implements IVargaD8 {
    D8;

    @Override
    public IVargaD8[] all() {
        return values();
    }
}
