/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Vimsamsa  	D-20  	Religious and spiritual matters
 * <p>
 * Vimsamsa Chart (D-20) –Each rasi is divided into 20 equal parts of 1 degree 30′ each.
 * Bodies in the 20 parts of a rasi go into the 20 rasis starting from Ar, Sg and Le,
 * based on whether the rasi is movable, fixed or dual.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD20 extends IVarga {

    @Override
    default int fid() {
        return 20;
    }

    @Override
    default String code() {
        return D20_CD;
    }
}
