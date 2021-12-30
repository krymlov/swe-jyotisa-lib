/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasa extends IKundaliSequence<IVimsottariDasa> {
    IGraha lord();

    String SYVD_CD = "SYVD";
    String CHVD_CD = "CHVD";
    String MAVD_CD = "MAVD";
    String RAVD_CD = "RAVD";
    String GUVD_CD = "GUVD";
    String SAVD_CD = "SAVD";
    String BUVD_CD = "BUVD";
    String KEVD_CD = "KEVD";
    String SKVD_CD = "SKVD";
}
