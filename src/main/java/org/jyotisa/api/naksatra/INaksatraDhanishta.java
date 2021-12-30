/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 23. Dhanishta
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraDhanishta extends INaksatra {


    @Override
    default int fid() {
        return 23;
    }

    @Override
    default String code() {
        return N23_CD;
    }

}
