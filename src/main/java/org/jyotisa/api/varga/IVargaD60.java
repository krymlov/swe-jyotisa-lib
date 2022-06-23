/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Shashtyamsa  	D-60 	   Balance sheet of past lives
 * <p>
 * Shashtyamsa Chart (D-60) – Each rasi is divided into 60 equal parts of 30′ each.
 * Bodies in the 60 parts of a rasi go into the 60 rasis starting the rasi itself.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD60 extends IVarga {

    @Override
    default int fid() {
        return 60;
    }

    @Override
    default String code() {
        return D60_CD;
    }
}
