/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD6;

/**
 * 6 Shashthamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD6 implements IVargaD6 {
    D6;

    @Override
    public IVargaD6[] all() {
        return values();
    }
}
