/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara.naksatra;


import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.naksatra.INaksatraPadaGrahaEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class NaksatraPadaGrahaEntity extends NaksatraPadaEntity implements INaksatraPadaGrahaEntity {
    private static final long serialVersionUID = -8762069279279700148L;

    protected final IGraha graha;

    public NaksatraPadaGrahaEntity(final double longitude, final IGraha graha,
                                   final INaksatraPada padaEnum, final double julday) {
        super(longitude, padaEnum, julday);
        this.graha = graha;
    }

    @Override
    public IGraha graha() {
        return graha;
    }

    @Override
    public String toString() {
        return toBuilder(graha.code()).toString();
    }
}
