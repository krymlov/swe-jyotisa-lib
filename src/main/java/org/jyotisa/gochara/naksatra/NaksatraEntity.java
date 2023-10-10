/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.naksatra.INaksatraEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class NaksatraEntity extends KundaliSequenceEntity<INaksatra> implements INaksatraEntity {
    private static final long serialVersionUID = 5568001391199497118L;

    public NaksatraEntity(final double longitude, final INaksatra naksatra, final double julday) {
        super(longitude, naksatra, julday);
    }

    @Override
    protected String printCode() {
        return entityEnum.name();
    }
}
