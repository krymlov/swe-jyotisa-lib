/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

/**
 * 2. Chandravaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaaraChandra extends IVaara {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return CHVR_CD;
    }

}
