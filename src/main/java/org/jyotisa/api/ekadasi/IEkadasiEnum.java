/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022-01
 */
package org.jyotisa.api.ekadasi;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public interface IEkadasiEnum extends IKundaliSequence<IEkadasiEnum> {
    IEkadasi ekadasi();

    @Override
    default int fid() {
        return ekadasi().fid();
    }

    @Override
    default String code() {
        return ekadasi().code();
    }

    @Override
    default double length() {
        return ekadasi().length();
    }
}
