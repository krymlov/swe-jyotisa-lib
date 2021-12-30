/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 11 Uccha - Exaltation
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityUccha extends IDignity {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return UCC_CD;
    }

    @Override
    default int power() {
        return 100;
    }
}
