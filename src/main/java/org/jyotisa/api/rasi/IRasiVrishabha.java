/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.rasi;

/**
 * 2.  Vṛṣabha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IRasiVrishabha extends IRasi {
    @Override
    default boolean fixed() {
        return true;
    }

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return R02_CD;
    }

}
