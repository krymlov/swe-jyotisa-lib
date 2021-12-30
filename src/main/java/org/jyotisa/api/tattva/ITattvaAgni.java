/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.tattva;

/**
 * 2. AGNI
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITattvaAgni extends ITattva {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return TT2_CD;
    }

}
