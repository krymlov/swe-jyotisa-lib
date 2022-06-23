/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Dasamsa  	D-10  	Career, Karma and Achievements
 * <p>
 * Dasamsa Chart (D-10) –Each rasi is divided into 10 equal parts of 3 degrees each.
 * Ten parts of a rasi go into the 10 rasis starting from the rasi itself or the 9th from it,
 * based on whether the rasi is an odd or even sign.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD10 extends IVarga {

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return D10_CD;
    }
}
