/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 5. Panchami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiPanchami extends ITithi {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return S05_CD;
    }

}
