/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 8. Ketu vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaKetu extends IVimsottariDasa {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return KEVD_CD;
    }

    @Override
    default double length() {
        return 7;
    }
}
