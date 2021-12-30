/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 9. Yamaghantaka
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaYamaghantaka extends IUpagraha {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return UG09_CD;
    }

}
