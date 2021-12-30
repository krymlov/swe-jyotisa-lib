/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.gochara.rasi;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.rasi.IRasiGrahaEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public class RasiGrahaEntity extends RasiEntity implements IRasiGrahaEntity {
    private static final long serialVersionUID = 2050113752429861293L;

    protected final IGraha graha;

    public RasiGrahaEntity(final double longitude, final IGraha graha,
                           final IRasi rasi, final double julday) {
        super(longitude, rasi, julday);
        this.graha = graha;
    }
    
    public IGraha getGraha() {
        return graha;
    }

    @Override
    public String toString() {
        return toBuilder(graha.code()).toString();
    }
}
