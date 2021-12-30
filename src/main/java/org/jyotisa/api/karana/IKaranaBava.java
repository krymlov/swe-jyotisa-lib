/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 1. Bava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaBava extends IKarana {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return KR01_CD;
    }

}
