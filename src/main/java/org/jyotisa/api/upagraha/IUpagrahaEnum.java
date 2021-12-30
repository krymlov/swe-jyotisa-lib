/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.upagraha;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IUpagrahaEnum extends IKundaliSequence<IUpagrahaEnum> {
    IUpagraha upagraha();

    @Override
    default int fid() {
        return upagraha().fid();
    }

    @Override
    default String code() {
        return upagraha().code();
    }

    @Override
    default double length() {
        return upagraha().length();
    }
}
