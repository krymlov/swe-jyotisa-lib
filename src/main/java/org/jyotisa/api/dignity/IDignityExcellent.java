/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 10 Uccha - Excellent
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityExcellent extends IDignity {

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return EXT_CD;
    }

    @Override
    default int power() {
        return 95;
    }
}
