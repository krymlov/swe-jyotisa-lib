/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 10. Dashami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiDashami extends ITithi {

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return S10_CD;
    }

}
