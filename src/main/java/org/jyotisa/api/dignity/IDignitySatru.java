/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 4 Satru
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignitySatru extends IDignity {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return SRU_CD;
    }

    @Override
    default int power() {
        return 6;
    }
}
