/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.api.nityayoga;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.NITYA_YOGA_LENGTH;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface INityaYogaEnum extends IKundaliSequence<INityaYogaEnum> {
    INityaYoga yoga();

    @Override
    default int fid() {
        return yoga().fid();
    }

    @Override
    default String code() {
        return yoga().code();
    }

    @Override
    default double length() {
        return NITYA_YOGA_LENGTH;
    }

}
