/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.karaka;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.d0;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface ICharaKaraka extends IKundaliSequence<ICharaKaraka> {
    @Override
    default double length() {
        return d0;
    }

    // Karakas
    String AK_CD = "AK";
    String AMK_CD = "AmK";
    String BK_CD = "BK";
    String MK_CD = "MK";
    String PIK_CD = "PiK";
    String PK_CD = "PK";
    String GK_CD = "GK";
    String DK_CD = "DK";
}
