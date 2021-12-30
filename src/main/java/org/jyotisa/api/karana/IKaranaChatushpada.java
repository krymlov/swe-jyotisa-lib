/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karana;

/**
 * 9. Chatushpada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKaranaChatushpada extends IKarana {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return KR09_CD;
    }

}
