/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface ILagna extends IKundaliSequence<ILagna> {

    @Override
    default double length() {
        return 0;
    }

    /** 0. Lagna */      String L0_CD = "L0";
    /** 1. Bhava */      String L1_CD = "L1";
    /** 2. Hora */       String L2_CD = "L2";
    /** 3. Ghati */      String L3_CD = "L3";
    /** 4. Vighati */    String L4_CD = "L4";
    /** 5. Varnada */    String L5_CD = "L5";
    /** 6. Sree */       String L6_CD = "L6";
    /** 7. Pranapada */  String L7_CD = "L7";
    /** 8. Indu */       String L8_CD = "L8";
}
