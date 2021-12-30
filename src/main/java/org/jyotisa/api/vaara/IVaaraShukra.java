/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

/**
 * 6. Shukravaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaaraShukra extends IVaara {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return SKVR_CD;
    }

}
