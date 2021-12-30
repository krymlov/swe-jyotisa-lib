/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 7. Vishti
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaVishti extends IKarana {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return KR07_CD;
    }

}
