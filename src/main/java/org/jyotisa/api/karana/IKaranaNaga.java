/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 10. Naga
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaNaga extends IKarana {

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return KR10_CD;
    }

}
