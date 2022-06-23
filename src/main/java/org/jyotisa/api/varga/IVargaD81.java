/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * 81 NavaNavāṁśa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD81 extends IVarga {

    @Override
    default int fid() {
        return 81;
    }

    @Override
    default String code() {
        return D81_CD;
    }
}
