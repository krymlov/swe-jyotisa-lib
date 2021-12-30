/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 5 Sama
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignitySama extends IDignity {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return SAM_CD;
    }

    @Override
    default int power() {
        return 12;
    }
}
