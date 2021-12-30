/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara.rasi;


import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class RasiEntity extends KundaliSequenceEntity<IRasi> implements IRasiEntity {
    private static final long serialVersionUID = -2813268558745473821L;

    public RasiEntity(final double longitude, final IRasi rasi, final double julday) {
        super(longitude, rasi, julday);
    }

    @Override
    protected String printCode() {
        return entityEnum.name();
    }
}
