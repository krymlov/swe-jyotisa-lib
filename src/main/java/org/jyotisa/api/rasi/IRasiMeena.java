/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 12. Mīna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiMeena extends IRasi {
    @Override
    default boolean dual() {
        return true;
    }

    @Override
    default int fid() {
        return 12;
    }

    @Override
    default String code() {
        return R12_CD;
    }

}
