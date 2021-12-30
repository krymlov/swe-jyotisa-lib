/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 5. Guru vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaGuru extends IVimsottariDasa {
    
    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return GUVD_CD;
    }

    @Override
    default double length() {
        return 16;
    }
}
