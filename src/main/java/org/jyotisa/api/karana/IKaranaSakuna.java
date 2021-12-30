/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 8. Sakuna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaSakuna extends IKarana {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return KR08_CD;
    }

}
