/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 6.  Kanyā
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiKanya extends IRasi {
    @Override
    default boolean dual() {
        return true;
    }

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return R06_CD;
    }

}
