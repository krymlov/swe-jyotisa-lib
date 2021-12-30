/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 2. Balava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaBalava extends IKarana {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return KR02_CD;
    }

}
