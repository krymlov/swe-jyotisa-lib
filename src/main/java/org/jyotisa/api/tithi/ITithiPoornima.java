/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 15. Purnima
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiPoornima extends ITithi {

    @Override
    default int fid() {
        return 15;
    }

    @Override
    default String code() {
        return S15_CD;
    }

}
