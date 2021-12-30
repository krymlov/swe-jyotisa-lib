/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 2. Chandra vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaChandra extends IVimsottariDasa {
    
    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return CHVD_CD;
    }

    @Override
    default double length() {
        return 10;
    }
}
