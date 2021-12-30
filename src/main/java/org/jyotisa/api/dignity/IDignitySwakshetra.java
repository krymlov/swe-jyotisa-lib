/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 8 Swakshetra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignitySwakshetra extends IDignity {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return SWT_CD;
    }

    @Override
    default int power() {
        return 50;
    }
}
