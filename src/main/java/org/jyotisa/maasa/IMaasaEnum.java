/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.maasa;

import org.jyotisa.api.IKundaliSequence;

/**
 * TODO: Not fully implemented...
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-12
 */
public interface IMaasaEnum extends IKundaliSequence<IMaasaEnum> {
    IMaasa maasa();

    @Override
    default int fid() {
        return maasa().fid();
    }

    @Override
    default String code() {
        return maasa().code();
    }

    @Override
    default double length() {
        return maasa().length();
    }
}
