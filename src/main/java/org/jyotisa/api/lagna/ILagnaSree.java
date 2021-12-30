/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.lagna;

/**
 * 6. Sree
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ILagnaSree extends ILagna {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return L6_CD;
    }

}
