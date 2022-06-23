/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD16;

/**
 * 16 Shodasamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD16 implements IVargaD16 {
    D16;

    @Override
    public IVargaD16[] all() {
        return values();
    }
}
