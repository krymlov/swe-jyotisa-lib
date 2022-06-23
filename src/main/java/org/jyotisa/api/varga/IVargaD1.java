/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Rasi  	D-1  	Physical Existence
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD1 extends IVarga {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return D01_CD;
    }
}
