/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Saptamsa  	D-7  	Environment related to children
 * <p>
 * Saptamsa Chart (D-7) –Each rasi is divided into 7 equal parts of 4 Degrees 17′ 8.57”.
 * The 1st, 2nd, 3rd, 4th, 5th, 6th and 7th parts of a rasi go into the seven rasis starting from the rasi
 * if it is an odd rasi or starting from the 7th sign from it, if it is an even rasi.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD7 extends IVarga {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return D07_CD;
    }
}
