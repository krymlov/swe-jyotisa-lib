/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 5. Varnada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaVarnada extends ILagna {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return L5_CD;
    }

}
