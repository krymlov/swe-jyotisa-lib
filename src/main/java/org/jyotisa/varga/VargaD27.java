/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.varga.IVargaD27;

/**
 * 27 Nakshatramsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD27 implements IVargaD27 {
    D27;

    @Override
    public IVargaD27[] all() {
        return values();
    }
}
