/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.vimsottari;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IVimsottariDasaEnum extends IKundaliSequence<IVimsottariDasaEnum> {
    IVimsottariDasa dasa();

    @Override
    default int fid() {
        return dasa().fid();
    }

    @Override
    default String code() {
        return dasa().code();
    }

    @Override
    default double length() {
        return dasa().length();
    }
}
