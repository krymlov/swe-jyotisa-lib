/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 13. Trayodashi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiTrayodasi extends ITithi {

    @Override
    default int fid() {
        return 13;
    }

    @Override
    default String code() {
        return S13_CD;
    }

}
