/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.tattva;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITattva extends IKundaliSequence<ITattva> {
    IGraha lord();

    @Override
    default double length() {
        return 0;
    }

    String TT1_CD = "TT1";
    String TT2_CD = "TT2";
    String TT3_CD = "TT3";
    String TT4_CD = "TT4";
    String TT5_CD = "TT5";
}
