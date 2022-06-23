/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   201911
 */

package org.jyotisa.api.varga;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.rasi.IRasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 201911
 */
public interface IVargaEnum extends IKundaliSequence<IVargaEnum> {
    IVarga varga();

    @Override
    default int fid() {
        return varga().fid();
    }

    @Override
    default String code() {
        return varga().code();
    }

    @Override
    default double length() {
        return varga().length();
    }

    default IRasi rasi(final double longitude) {
        return varga().rasi(longitude);
    }
}
