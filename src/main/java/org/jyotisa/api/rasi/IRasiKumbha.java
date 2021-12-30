/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 11. Kumbha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiKumbha extends IRasi {
    @Override
    default boolean fixed() {
        return true;
    }

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return R11_CD;
    }

}
