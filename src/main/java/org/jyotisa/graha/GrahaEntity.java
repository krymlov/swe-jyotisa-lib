/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.graha;


import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.app.KundaliSequenceEntity;
import org.jyotisa.bhava.EBhava;
import org.swisseph.api.ISweObjects;

import static org.jyotisa.varga.VargaD1.D1;
import static org.swisseph.api.ISweConstants.CH_VS;
import static org.swisseph.utils.IDegreeUtils.toDMSms;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class GrahaEntity extends KundaliSequenceEntity<IGraha> implements IGrahaEntity {
    protected final double latitude;
    protected final boolean vakri;
    protected final IBhava bhava;

    protected ICharaKaraka charaKaraka;

    public GrahaEntity(final IGraha graha, final ISweObjects sweObjects) {
        super(sweObjects.longitudes()[graha.uid()], graha, sweObjects.sweJulianDate().julianDay());

        final int uid = graha.uid();
        this.vakri = sweObjects.retrogrades()[uid];
        this.latitude = sweObjects.latitudes()[uid];
        this.bhava = EBhava.byUid(sweObjects.houses()[uid]);
    }

    @Override
    public double latitude() {
        return latitude;
    }

    @Override
    public IBhava bhava() {
        return bhava;
    }

    @Override
    public boolean vakri() {
        return vakri;
    }

    @Override
    public IDignity dignity() {
        return dignity(D1);
    }

    @Override
    public IDignity dignity(final IVarga varga) {
        return entityEnum.dignity(varga, longitude);
    }

    public void charaKaraka(ICharaKaraka charaKaraka) {
        this.charaKaraka = charaKaraka;
    }

    public ICharaKaraka charaKaraka() {
        return charaKaraka;
    }

    @Override
    public String toString() {
        final IDignity dignity = dignity();
        return new StringBuilder(64)
                .append(entityEnum.code()).append(CH_VS)
                .append(D1.code()).append(CH_VS)
                .append(pada().name()).append(CH_VS)
                .append(bhava == null ? "?" : bhava.code()).append(CH_VS)
                .append(vakri ? "R" : "D").append(CH_VS)
                .append((charaKaraka == null ? "?" : charaKaraka.code())).append(CH_VS)
                .append((dignity == null ? "?" : dignity.code())).append(CH_VS)
                .append(toDMSms(longitude)).append(CH_VS)
                .append(toDMSms(latitude)).append(CH_VS)
                .toString();
    }
}
