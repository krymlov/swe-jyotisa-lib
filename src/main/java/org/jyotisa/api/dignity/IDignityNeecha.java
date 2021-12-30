/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 1 Neecha - Debilitation
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityNeecha extends IDignity {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return NEE_CD;
    }

    @Override
    default int power() {
        return 0;
    }
}
