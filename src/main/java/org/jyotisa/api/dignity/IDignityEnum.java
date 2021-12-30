/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.api.dignity;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IDignityEnum extends IKundaliSequence<IDignityEnum> {
    IDignity dignity();

    @Override
    default int fid() {
        return dignity().fid();
    }

    @Override
    default String code() {
        return dignity().code();
    }

    @Override
    default double length() {
        return dignity().length();
    }
}
