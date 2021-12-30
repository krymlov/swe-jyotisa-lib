/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 10. Makara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiMakara extends IRasi {

    @Override
    default boolean movable() {
        return true;
    }

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return R10_CD;
    }

}
