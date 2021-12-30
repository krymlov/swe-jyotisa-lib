/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 7 Adhimitra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityAdhimitra extends IDignity {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return ADM_CD;
    }

    @Override
    default int power() {
        return 37;
    }
}
