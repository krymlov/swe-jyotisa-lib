/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.naksatra.INaksatraPadaEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class NaksatraPadaEntity extends KundaliSequenceEntity<INaksatraPada> implements INaksatraPadaEntity {
    private static final long serialVersionUID = -3173738151587074584L;

    public NaksatraPadaEntity(final double longitude, final INaksatraPada padaEnum, final double julday) {
        super(longitude, padaEnum, julday);
    }

    @Override
    protected String printCode() {
        return entityEnum.name();
    }
}
