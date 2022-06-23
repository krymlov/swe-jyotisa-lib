/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Rudramsa  	D-11  	Death and Destruction
 * <p>
 * Rudramsa Chart (D-11) – Each rasi is divided into 11 equal parts of 2 degrees 43′ 38” each.
 * Count rasis from Ar to the rasi being divided, in the zodiacal order.
 * Count the same number of rasis anti-zodiacally from Ar.
 * <p>
 * Bodies in the 11 parts of the rasi go into the 11 rasis starting from the rasi found thus.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD11 extends IVarga {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return D11_CD;
    }
}
