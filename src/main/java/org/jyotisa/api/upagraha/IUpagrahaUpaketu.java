/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 5. Upaketu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaUpaketu extends IUpagraha {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return UG05_CD;
    }

}
