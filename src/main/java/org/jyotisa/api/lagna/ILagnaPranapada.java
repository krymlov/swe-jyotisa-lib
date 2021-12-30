/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 7. Pranapada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaPranapada extends ILagna {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return L7_CD;
    }

}
