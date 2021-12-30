/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 4. Vighati
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaVighati extends ILagna {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return L4_CD;
    }

}
