/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2018-02
 */

package org.jyotisa.graha.shani;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.rasi.IRasiEnum;
import org.jyotisa.rasi.ERasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2018-02
 */
public class ShaniSadeSati extends SaChGochara {

    public ShaniSadeSati(final IKundali kundali) {
        super(kundali, 4);
    }

    protected void addGocharaLongitudes(final StringBuilder gocharaArgsBuilder) {
        IRasiEnum rasi = ERasi.byRasi(kundali.grahas().chandra().pada().rasi()).previous();
        super.startDegree = rasi.segment().start();
        gocharaArgsBuilder.append(" -lon");

        for (int i = 0; i <= 3; i++, rasi = rasi.following()) {
            if (i > 0) gocharaArgsBuilder.append('/');
            gocharaArgsBuilder.append(rasi.segment().start());
        }
    }
}
