/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.api.tithi;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.graha.IGraha;
import org.swisseph.api.ISweSegment;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface ITithiEnum extends IKundaliSequence<ITithiEnum> {
    ITithi tithi();

    default IPaksa paksa() {
        return tithi().paksa();
    }

    default IGraha lord() {
        return tithi().lord();
    }

    @Override
    default ISweSegment segment() {
        return tithi().segment();
    }

    @Override
    default double length() {
        return tithi().length();
    }

    @Override
    default String code() {
        return tithi().code();
    }

    @Override
    default int fid() {
        return tithi().fid();
    }

}
