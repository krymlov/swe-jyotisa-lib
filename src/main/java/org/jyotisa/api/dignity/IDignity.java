/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignity extends IKundaliSequence<IDignity> {
    int power();

    @Override
    default double length() {
        return power();
    }

    // Dignity
    String NEE_CD = "DG1";
    String DET_CD = "DG2";
    String ADS_CD = "DG3";
    String SRU_CD = "DG4";
    String SAM_CD = "DG5";
    String MIR_CD = "DG6";
    String ADM_CD = "DG7";
    String SWT_CD = "DG8";
    String MLT_CD = "DG9";
    String EXT_CD = "DG10";
    String UCC_CD = "DG11";
}
