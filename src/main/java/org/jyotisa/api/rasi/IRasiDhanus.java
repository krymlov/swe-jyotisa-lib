/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 9. Dhanus
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiDhanus extends IRasi {
    @Override
    default boolean dual() {
        return true;
    }

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return R09_CD;
    }

}
