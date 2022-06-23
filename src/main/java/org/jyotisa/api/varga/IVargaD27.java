/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Nakshatramsa  	D-27  	Strengths and Weaknesses and inherent nature
 * <p>
 * Nakshatramsa Chart (D-27) –Each rasi is divided into 27 equal parts of 1 degree 6′ 40” each.
 * Bodies in the 27 parts of a rasi go into the 12 rasis starting from Ar, Cn, Li and Cp
 * based on whether the rasi is a fiery, earthy, airy or watery rasi.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD27 extends IVarga {

    @Override
    default int fid() {
        return 27;
    }

    @Override
    default String code() {
        return D27_CD;
    }
}
