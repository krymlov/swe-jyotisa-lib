/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Panchamsa  	D-5  	Fame, Authority and Power
 * <p>
 * Panchamsa Chart (D-5) – Each rasi is divided into 5 equal parts of six degrees each
 * and 5 parts of an odd rasi go into Ar, Aq, Sg, Ge and Li
 * and 5 parts of an even rasi go into Ta, Vi, Pi, Cp and Sc.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD5 extends IVarga {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return D05_CD;
    }
}
