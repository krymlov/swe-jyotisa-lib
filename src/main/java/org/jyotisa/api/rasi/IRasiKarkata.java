/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 4.  Karkata
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiKarkata extends IRasi {

    @Override
    default boolean movable() {
        return true;
    }

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return R04_CD;
    }

}
