/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 1.  Meṣa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiMesha extends IRasi {

    @Override
    default boolean movable() {
        return true;
    }

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return R01_CD;
    }

}
