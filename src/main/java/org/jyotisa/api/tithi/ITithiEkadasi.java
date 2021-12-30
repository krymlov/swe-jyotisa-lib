/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 11. Ekadasi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiEkadasi extends ITithi {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return S11_CD;
    }

}
