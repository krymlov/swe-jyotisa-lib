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

        // a whole-sign bhava is this point's own sign counted from the ascendant's, so without
        // an ascendant there is no bhava to report. The arithmetic below does not fail on a
        // missing one - it quietly answers "sign + 1", which reads like a real bhava and is not.
        this.bhava = sweObjects.isCalculated(ISweObjects.LG)
                ? EBhava.byUid((pada().rasi().fid() + i12 - sweObjects.signs()[ISweObjects.LG]) % i12 + 1)
                : EBhava.NIL.bhava();
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
