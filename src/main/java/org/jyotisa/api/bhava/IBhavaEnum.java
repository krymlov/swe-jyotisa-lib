/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.api.bhava;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.BHAVA_LENGTH;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IBhavaEnum extends IKundaliSequence<IBhavaEnum> {
    IBhava bhava();

    @Override
    default int fid() {
        return bhava().fid();
    }

    @Override
    default String code() {
        return bhava().code();
    }

    @Override
    default double length() {
        return BHAVA_LENGTH;
    }

}
