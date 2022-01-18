/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.api.ekadasi;


public interface IUtpannaEkadasi extends IEkadasi {
    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return EK01_CD;
    }
}
