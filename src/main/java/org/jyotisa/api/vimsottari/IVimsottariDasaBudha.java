/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 7. Budha vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaBudha extends IVimsottariDasa {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return BUVD_CD;
    }

    @Override
    default double length() {
        return 17;
    }
}
