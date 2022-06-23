/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Shashthamsa  	D-6  	Health
 * <p>
 * Shashthamsa Chart (D-6) – Each rasi is divided into 6 equal parts of 5 degrees each
 * and distributed in the six parts of a rasi from Ar or Li, based on whether the rasi is odd or even.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD6 extends IVarga {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return D06_CD;
    }
}
