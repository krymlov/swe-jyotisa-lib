/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Navamsa  	D-9  	Marriage, Spouse, Dharma, Skills
 * <p>
 * Navamsa Chart (D-9) – Each rasi is divided into 9 equal parts of 3 degrees 20′ each.
 * Nine parts of a rasi go into the 9 rasis starting from Ar, Cp, Li or Cn
 * based on whether the rasi is a fiery, earthy, airy or watery sign.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD9 extends IVarga {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return D09_CD;
    }
}
