/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 8.  Vṛścika
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiVrischika extends IRasi {
    @Override
    default boolean fixed() {
        return true;
    }

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return R08_CD;
    }


}
