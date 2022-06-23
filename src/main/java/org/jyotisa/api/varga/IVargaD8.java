/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Ashtamsa  	D-8  	Legal Issues, Sudden and Unexpected troubles
 * <p>
 * Ashtamsa Chart (D-8) – Each rasi is divided into 8 equal parts of 3 degrees 45′ each.
 * Eight parts of a rasi go into the 8 rasis starting from Ar, Sg or Le,
 * based on whether the rasi is a movable, fixed or dual sign.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD8 extends IVarga {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return D08_CD;
    }
}
