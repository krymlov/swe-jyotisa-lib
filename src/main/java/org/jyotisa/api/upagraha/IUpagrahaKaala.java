/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 6. Kaala
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaKaala extends IUpagraha {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return UG06_CD;
    }

}
