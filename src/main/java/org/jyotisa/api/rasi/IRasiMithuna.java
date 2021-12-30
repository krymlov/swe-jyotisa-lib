/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 3.  Mithuna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiMithuna extends IRasi {
    @Override
    default boolean dual() {
        return true;
    }

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return R03_CD;
    }

}
