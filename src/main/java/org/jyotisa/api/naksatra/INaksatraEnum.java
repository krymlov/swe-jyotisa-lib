/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.naksatra;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.NAKSHATRA_LENGTH;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface INaksatraEnum extends IKundaliSequence<INaksatraEnum> {
    INaksatra naksatra();
    INaksatraPada pada(int pada);

    @Override
    default int fid() {
        return naksatra().fid();
    }

    @Override
    default String code() {
        return naksatra().code();
    }

    @Override
    default double length() {
        return NAKSHATRA_LENGTH;
    }

}
