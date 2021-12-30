/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 0. Lagna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaJanma extends ILagna {

    @Override
    default int fid() {
        return 0;
    }

    @Override
    default String code() {
        return L0_CD;
    }

}
