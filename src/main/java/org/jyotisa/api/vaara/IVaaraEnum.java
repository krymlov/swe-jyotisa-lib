/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.vaara;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IVaaraEnum extends IKundaliSequence<IVaaraEnum> {
    IVaara vaara();

    @Override
    default String code() {
        return vaara().code();
    }

    @Override
    default int fid() {
        return vaara().fid();
    }

    @Override
    default double length() {
        return vaara().length();
    }

}
