/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 1. Surya vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaSurya extends IVimsottariDasa {
    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return SYVD_CD;
    }

    @Override
    default double length() {
        return 6;
    }
}
