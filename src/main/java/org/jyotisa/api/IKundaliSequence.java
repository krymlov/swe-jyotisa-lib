/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api;

import org.swisseph.api.ISweEnumSequence;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IKundaliSequence<E extends IKundaliSequence<E>>
        extends ISweEnumSequence<E>, IKundaliSegment {

    @Override
    default int uid() {
        return fid();
    }

}
