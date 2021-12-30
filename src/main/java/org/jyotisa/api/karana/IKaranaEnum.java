/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.karana;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.*;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IKaranaEnum extends IKundaliSequence<IKaranaEnum> {
    double TH14th2ndP00 = d342;
    double TH14th2ndP06 = TH14th2ndP00 + d6;
    double TH14th2ndP12 = TH14th2ndP00 + d12;
    double TH14th2ndP18 = TH14th2ndP00 + d18;

    IKarana karana();

    @Override
    default int fid() {
        return karana().fid();
    }

    @Override
    default String code() {
        return karana().code();
    }

    @Override
    default double length() {
        return KARANA_LENGTH;
    }

}
