/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 2. Hora
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaHora extends ILagna {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return L2_CD;
    }

}
