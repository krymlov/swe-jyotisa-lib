/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 1. Bhava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaBhava extends ILagna {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return L1_CD;
    }

}
