/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Hora  	D-2  	Wealth, Resources and Money
 * <p>
 * Hora Chart (D-2) – Each rasi is divided into two equal parts of fifteen degrees each.
 * Bodies in the first fifteen degrees of odd rasis are in Sun’s hora and bodies
 * in the second fifteen degrees of odd rasis are in Moon’s hora and vice versa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD2 extends IVarga {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return D02_CD;
    }
}
