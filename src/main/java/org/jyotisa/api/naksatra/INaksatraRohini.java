/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 4 Rohini
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraRohini extends INaksatra {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return N04_CD;
    }

}
