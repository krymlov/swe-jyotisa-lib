/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD20;

/**
 * 20 Vimsamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD20 implements IVargaD20 {
    D20;

    @Override
    public IVargaD20[] all() {
        return values();
    }
}
