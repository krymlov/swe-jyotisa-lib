/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 6. Vanija
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaVanija extends IKarana {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return KR06_CD;
    }

}
