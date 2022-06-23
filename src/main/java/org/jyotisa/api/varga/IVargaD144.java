/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * 144 Dvadasdvadasamsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD144 extends IVarga {

    @Override
    default int fid() {
        return 144;
    }

    @Override
    default String code() {
        return D144_CD;
    }
}
