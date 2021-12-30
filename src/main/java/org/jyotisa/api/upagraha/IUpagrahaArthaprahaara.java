/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 8. Arthaprahaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaArthaprahaara extends IUpagraha {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return UG08_CD;
    }

}
