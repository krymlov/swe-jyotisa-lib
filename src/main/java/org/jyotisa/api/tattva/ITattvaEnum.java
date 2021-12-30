/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.tattva;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface ITattvaEnum extends IKundaliSequence<ITattvaEnum> {
    ITattva tattva();

    @Override
    default double length() {
        return tattva().length();
    }

    @Override
    default String code() {
        return tattva().code();
    }

    @Override
    default int fid() {
        return tattva().fid();
    }
}
