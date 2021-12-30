/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.upagraha;

import org.jyotisa.api.IKundaliSequence;
import org.swisseph.api.ISweSegment;

import static org.swisseph.app.SweSegment.ZERO_SEGMENT;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IUpagraha extends IKundaliSequence<IUpagraha> {

    @Override
    default double length() {
        return 0;
    }

    default ISweSegment segment() {
        return ZERO_SEGMENT;
    }

    /**  1. Dhuma */         String UG01_CD  = "UG1";
    /**  2. Vyatipaata */    String UG02_CD  = "UG2";
    /**  3. Parivesha */     String UG03_CD  = "UG3";
    /**  4. Indrachaapa */   String UG04_CD  = "UG4";
    /**  5. Upaketu */       String UG05_CD  = "UG5";
    /**  6. Kaala */         String UG06_CD  = "UG6";
    /**  7. Mrityu */        String UG07_CD  = "UG7";
    /**  8. Arthaprahaara */ String UG08_CD  = "UG8";
    /**  9. Yamaghantaka */  String UG09_CD  = "UG9";
    /** 10. Gulika */        String UG10_CD = "UG10";
    /** 11. Maandi */        String UG11_CD = "UG11";
}
