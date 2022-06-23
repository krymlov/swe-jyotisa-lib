/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Trimsamsa Chart (D-30) positions of planets are computed based Odd and Even Rasis.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD30 extends IVarga {

    @Override
    default int fid() {
        return 30;
    }

    @Override
    default String code() {
        return D30_CD;
    }
}
