/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Akshavedamsa  	D-45  	General Issues
 * <p>
 * Akshavedamsa Chart (D-45) – Each rasi is divided into 45 equal parts of 40′ each.
 * Bodies in the 45 parts of a rasi go into the 45 rasis starting from Ar, Le or Sg,
 * based on whether the rasi is a movable, fixed or dual rasi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD45 extends IVarga {

    @Override
    default int fid() {
        return 45;
    }

    @Override
    default String code() {
        return D45_CD;
    }
}
