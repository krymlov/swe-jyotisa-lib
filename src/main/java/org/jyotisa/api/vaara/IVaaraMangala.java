/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

/**
 * 3. Mangalavaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaaraMangala extends IVaara {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return MAVR_CD;
    }
}
