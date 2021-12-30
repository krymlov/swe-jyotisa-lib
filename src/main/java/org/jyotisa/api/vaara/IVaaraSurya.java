/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

/**
 * 1. Sūryavāra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaaraSurya extends IVaara {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return SYVR_CD;
    }

}
