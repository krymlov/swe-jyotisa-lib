/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.lagna;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface ILagnaEnum extends IKundaliSequence<ILagnaEnum> {
    ILagna lagna();

    @Override
    default int fid() {
        return lagna().fid();
    }

    @Override
    default String code() {
        return lagna().code();
    }

    @Override
    default double length() {
        return lagna().length();
    }
}
