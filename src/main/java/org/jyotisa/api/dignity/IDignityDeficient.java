/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 2 Neecha - Deficient
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityDeficient extends IDignity {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return DET_CD;
    }

    @Override
    default int power() {
        return 1;
    }
}
