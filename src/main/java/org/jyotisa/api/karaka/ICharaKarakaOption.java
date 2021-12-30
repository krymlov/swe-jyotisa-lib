/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.karaka;

import org.jyotisa.api.IKundaliEnum;
import org.jyotisa.api.graha.IGrahaEntity;

import java.util.Comparator;

import static org.swisseph.api.ISweConstants.d30;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.1, 2021-01
 */
public interface ICharaKarakaOption extends IKundaliEnum, Comparator<IGrahaEntity> {
    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return CK7_CD;
    }

    @Override
    default int compare(final IGrahaEntity graha2, final IGrahaEntity graha1) {
        double d1 = graha1.longitude(), d2 = graha2.longitude();
        if (fid() == 7) return Double.compare(d1 % d30, d2 % d30);
        if (RA == graha1.entityEnum().uid()) d1 = fix360(d30 - d1);
        if (RA == graha2.entityEnum().uid()) d2 = fix360(d30 - d2);
        return Double.compare(d1 % d30, d2 % d30);
    }

    String CK7_CD = "CK7";
    String CK8_CD = "CK8";
}
