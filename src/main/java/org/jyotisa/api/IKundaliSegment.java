/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api;

import org.swisseph.api.ISweSegment;
import org.swisseph.app.SweSegment;

import static org.swisseph.api.ISweConstants.i1;
import static org.swisseph.app.SweSegment.ZERO_SEGMENT;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IKundaliSegment extends IKundaliEnum {
    double length();

    default ISweSegment segment() {
        if (0 == fid() && NIL_CD.equals(code())) {
            return ZERO_SEGMENT;
        }

        final int fid = fid();
        final double length = length();
        return new SweSegment((fid - i1) * length, fid * length);
    }

}
