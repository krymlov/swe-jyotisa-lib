/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 6. Ardra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraArdra extends INaksatra {


    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return N06_CD;
    }

}
