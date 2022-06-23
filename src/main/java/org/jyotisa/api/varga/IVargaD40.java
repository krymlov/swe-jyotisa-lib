/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Khavedamsa  	D-40  	Auspicious and Inauspicious events
 * <p>
 * Khavedamsa Chart (D-40) – Each rasi is divided into 40 equal parts of 45′ each.
 * Bodies in the 40 parts of a rasi go into the 40 rasis starting from Ar or Li,
 * based on whether the rasi is odd or even.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD40 extends IVarga {

    @Override
    default int fid() {
        return 40;
    }

    @Override
    default String code() {
        return D40_CD;
    }
}
