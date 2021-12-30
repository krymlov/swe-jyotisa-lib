/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;

import static org.swisseph.api.ISweConstants.VAARA_LENGTH;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaara extends IKundaliSequence<IVaara> {
    IGraha lord();

    @Override
    default double length() {
        return VAARA_LENGTH;
    }

    String SYVR_CD = "SYVR";
    String CHVR_CD = "CHVR";
    String MAVR_CD = "MAVR";
    String GUVR_CD = "GUVR";
    String SAVR_CD = "SAVR";
    String BUVR_CD = "BUVR";
    String SKVR_CD = "SKVR";

}
