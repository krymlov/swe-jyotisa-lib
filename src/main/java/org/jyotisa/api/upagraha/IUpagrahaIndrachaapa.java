/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 4. Indrachaapa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaIndrachaapa extends IUpagraha {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return UG04_CD;
    }

}
