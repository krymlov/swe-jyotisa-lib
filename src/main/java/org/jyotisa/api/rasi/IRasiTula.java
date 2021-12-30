/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 7.  Tulā
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiTula extends IRasi {

    @Override
    default boolean movable() {
        return true;
    }

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return R07_CD;
    }

}
