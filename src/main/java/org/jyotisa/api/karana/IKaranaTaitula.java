/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 4. Taitula
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaTaitula extends IKarana {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return KR04_CD;
    }

}
