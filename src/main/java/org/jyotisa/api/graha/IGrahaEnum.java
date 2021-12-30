/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.api.graha;

import org.jyotisa.api.IKundaliSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-10
 */
public interface IGrahaEnum extends IKundaliSequence<IGrahaEnum> {
    IGraha graha();

    @Override
    default int fid() {
        return graha().fid();
    }

    @Override
    default String code() {
        return graha().code();
    }

    @Override
    default double length() {
        return graha().length();
    }
}
