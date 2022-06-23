/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Drekkana  	D-3  	Environment related to Brothers and Sisters
 * <p>
 * Drekkana Chart (D3) – Each rasi is divided into three equal parts of ten degrees
 * and each degree elements are placed in 1st, 5th and 9th Rasis.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD3 extends IVarga {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return D03_CD;
    }
}
