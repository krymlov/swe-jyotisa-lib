/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.rasi;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.RASI_LENGTH;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IRasiEnum extends IKundaliSequence<IRasiEnum> {
    IRasi rasi();

    @Override
    default int fid() {
        return rasi().fid();
    }

    @Override
    default String code() {
        return rasi().code();
    }

    @Override
    default double length() {
        return RASI_LENGTH;
    }
}
