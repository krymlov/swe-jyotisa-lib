/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * 108 Ashtottaramsa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD108 extends IVarga {

    @Override
    default int fid() {
        return 108;
    }

    @Override
    default String code() {
        return D108_CD;
    }
}
