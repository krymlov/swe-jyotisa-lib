/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.upagraha;


import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.upagraha.IUpagraha;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.app.KundaliSequenceEntity;
import org.jyotisa.bhava.EBhava;
import org.swisseph.api.ISweObjects;

import static org.swisseph.api.ISweConstants.CH_VS;
import static org.swisseph.api.ISweConstants.i12;
import static org.swisseph.utils.IDegreeUtils.toDMSms;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class UpagrahaEntity extends KundaliSequenceEntity<IUpagraha> implements IUpagrahaEntity {
    protected final IBhava bhava;

    public UpagrahaEntity(final IUpagraha upagraha, final ISweObjects sweObjects, final double longitude) {
        super(longitude, upagraha, sweObjects.sweJulianDate().julianDay());

        final int lagnaSign = sweObjects.signs()[ISweObjects.LG];
        this.bhava = EBhava.byUid((pada().rasi().fid() + i12 - lagnaSign) % i12 + 1);
    }
    
    @Override
    public IBhava bhava() {
        return bhava;
    }

    @Override
    public String toString() {
        return new StringBuilder(100)
            .append(entityEnum.name()).append(CH_VS)
            .append(pada().name()).append(CH_VS)
            .append(bhava.name()).append(CH_VS)
            .append(toDMSms(longitude)).append(CH_VS)
            .toString();
    }
}
