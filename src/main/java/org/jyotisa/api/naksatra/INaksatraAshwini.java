/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 1. Ashwini
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraAshwini extends INaksatra {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return N01_CD;
    }

}
