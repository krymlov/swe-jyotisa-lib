/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 9. Navami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiNavami extends ITithi {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return S09_CD;
    }

}
