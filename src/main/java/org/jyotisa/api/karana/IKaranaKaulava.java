/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 3. Kaulava
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaKaulava extends IKarana {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return KR03_CD;
    }

}
