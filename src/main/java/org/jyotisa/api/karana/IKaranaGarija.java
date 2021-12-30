/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 5. Garija
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaGarija extends IKarana {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return KR05_CD;
    }

}
