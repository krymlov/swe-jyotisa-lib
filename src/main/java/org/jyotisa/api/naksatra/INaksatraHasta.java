/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 13. Hasta
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraHasta extends INaksatra {


    @Override
    default int fid() {
        return 13;
    }

    @Override
    default String code() {
        return N13_CD;
    }

}
