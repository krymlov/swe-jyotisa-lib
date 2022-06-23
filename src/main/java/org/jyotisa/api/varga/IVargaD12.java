/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Dwadasamsa	D-12  	Environment related to parents
 * <p>
 * Dwadasamsa Chart (D-12) – Each rasi is divided into 12 equal parts of 2 degrees 30′ each.
 * Twelve parts of a rasi go into the 12 rasis starting from the rasi itself.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD12 extends IVarga {

    @Override
    default int fid() {
        return 12;
    }

    @Override
    default String code() {
        return D12_CD;
    }
}
