/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 9. Shukra vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaShukra extends IVimsottariDasa {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return SKVD_CD;
    }

    @Override
    default double length() {
        return 10;
    }
}
