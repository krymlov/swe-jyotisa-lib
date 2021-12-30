/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.app;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.IKundaliSequenceEntity;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.naksatra.ENaksatraPada;
import org.swisseph.app.SweEnumEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public class KundaliSequenceEntity<E extends IKundaliSequence<E>>
        extends SweEnumEntity<E> implements IKundaliSequenceEntity<E> {
    private static final long serialVersionUID = 5441952858607969589L;

    protected KundaliSequenceEntity(double longitude, E entityEnum, double julianDay) {
        super(longitude, entityEnum, julianDay);
    }

    @Override
    public INaksatraPada pada() {
        return ENaksatraPada.byLongitude(longitude);
    }
}
