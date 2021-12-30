/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.tattva;

/**
 * 1. AKASHA
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITattvaAkasha extends ITattva {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return TT1_CD;
    }

}
