/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD5;
import org.jyotisa.rasi.ERasi;

import static org.swisseph.api.ISweConstants.*;

/**
 * 5 Panchamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD5 implements IVargaD5 {
    D5;

    @Override
    public IVargaD5[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        final double part = (d30 / 5);
        final double rem = longitude % i30;
        final int pos = (int) (rem / part) + 1;
        int uid = (int) (longitude / i30) + 1;

        if ((uid % 2) != i0) {
            switch (pos) {
                case 1: uid = i1; break;
                case 2: uid = i11; break;
                case 3: uid = i9; break;
                case 4: uid = i3; break;
                case 5: uid = i7; break;
            }
        } else {
            switch (pos) {
                case 1: uid = i2; break;
                case 2: uid = i6; break;
                case 3: uid = i12; break;
                case 4: uid = i10; break;
                case 5: uid = i8; break;
            }
        }

        if (uid == i0) uid = i12;
        return ERasi.byUid(uid);
    }

}
