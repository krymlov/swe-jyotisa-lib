/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.naksatra.INaksatraPadaEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class NaksatraPadaEntity extends KundaliSequenceEntity<INaksatraPada> implements INaksatraPadaEntity {
    private static final long serialVersionUID = -4906875689438419211L;

    protected final IGraha graha;

    public NaksatraPadaEntity(final double longitude, final IGraha graha,
                              final INaksatraPada padaEnum, final double julday) {
        super(longitude, padaEnum, julday);
        this.graha = graha;
    }

    @Override
    public IGraha graha() {
        return graha;
    }

    @Override
    protected String printCode() {
        return entityEnum.name();
    }

    @Override
    public String toString() {
        return toBuilder(graha.code()).toString();
    }
}
