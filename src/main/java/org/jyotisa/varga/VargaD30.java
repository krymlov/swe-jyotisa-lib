/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.varga;

import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IVargaD30;
import org.jyotisa.rasi.ERasi;

import static org.swisseph.api.ISweConstants.*;

/**
 * 30 Trimsamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum VargaD30 implements IVargaD30 {
    D30;

    @Override
    public IVargaD30[] all() {
        return values();
    }

    @Override
    public IRasi rasi(final double longitude) {
        final double part = (d30 / 30);
        final double rem = longitude % i30;
        final double pos = (int) (rem / part);
        int uid = (int) (longitude / i30) + 1;

        if ((uid % 2) == i0) {
            if (pos < 5) uid = 2;
            else if (5 <= pos && pos < i12) uid = 6;
            else if (i12 <= pos && pos < 20) uid = i12;
            else if (20 <= pos && pos < 25) uid = 10;
            else if (25 <= pos && pos < i30) uid = 8;
        } else {
            if (pos < 5) uid = 1;
            else if (5 <= pos && pos < 10) uid = 11;
            else if (10 <= pos && pos < 18) uid = 9;
            else if (18 <= pos && pos < 25) uid = 3;
            else if (25 <= pos && pos < i30) uid = 7;
        }

        if (uid == i0) uid = i12;
        return ERasi.byUid(uid);
    }

}
