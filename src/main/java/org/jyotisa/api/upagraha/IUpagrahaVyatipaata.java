/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 2. Vyatipaata
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaVyatipaata extends IUpagraha {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return UG02_CD;
    }

}
