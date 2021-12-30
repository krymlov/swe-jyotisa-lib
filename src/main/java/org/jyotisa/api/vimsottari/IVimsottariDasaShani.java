/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 6. Shani vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaShani extends IVimsottariDasa {
    
    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return SAVD_CD;
    }

    @Override
    default double length() {
        return 19;
    }
}
