/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Shodasamsa  	D-16  	Vehicle Related Pleasures
 * <p>
 * Shodasamsa Chart (D-16) – Each rasi is divided into 16 equal parts of 1 degree 52′ 30” each.
 * Bodies in the 16 parts of a rasi go into the 16 rasis starting from Ar, Le and Sg,
 * based on whether the rasi is movable, fixed or dual.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD16 extends IVarga {

    @Override
    default int fid() {
        return 16;
    }

    @Override
    default String code() {
        return D16_CD;
    }
}
