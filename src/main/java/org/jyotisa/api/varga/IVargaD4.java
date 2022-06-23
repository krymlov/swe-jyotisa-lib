/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Chaturthamsa  	D-4  	Residence, Houses, Properties and Fortune
 * <p>
 * Chaturthamsa Chart (D-4) – Each rasi is divided into four equal parts of 7.5 degrees each
 * and sequenced in the 1st, 4th, 7th and 10th from that rasi in Chaturthamsa.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVargaD4 extends IVarga {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return D04_CD;
    }
}
