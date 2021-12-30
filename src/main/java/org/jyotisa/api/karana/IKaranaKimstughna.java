/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 11. Kimstughna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaKimstughna extends IKarana {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return KR11_CD;
    }

}
