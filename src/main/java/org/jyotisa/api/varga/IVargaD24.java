/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Chaturvimsamsa  	D-24  	Learning, Knowledge and Education
 * <p>
 * Chaturvimsamsa Chart (D-24) – Each rasi is divided into 24 equal parts of 1 degree 15′ each.
 * Bodies in the 24 parts of a rasi go into the 24 rasis starting from Le or Cn,
 * based on whether the rasi is odd or even.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD24 extends IVarga {

    @Override
    default int fid() {
        return 24;
    }

    @Override
    default String code() {
        return D24_CD;
    }
}
