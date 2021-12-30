/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

/**
 * 4. Budhavaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaaraBudha extends IVaara {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return BUVR_CD;
    }

}
