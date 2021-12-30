/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 5.  Siṁha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiSimha extends IRasi {
    @Override
    default boolean fixed() {
        return true;
    }

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return R05_CD;
    }

}
