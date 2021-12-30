/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.naksatra.INaksatra;
import org.jyotisa.api.naksatra.INaksatraEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class NaksatraEntity extends KundaliSequenceEntity<INaksatra> implements INaksatraEntity {
    private static final long serialVersionUID = 2690311991342536272L;

    protected final IGraha graha;

    public NaksatraEntity(final double offset, final IGraha graha,
                          final INaksatra naksatra, final double julday) {
        super(offset, naksatra, julday);
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
