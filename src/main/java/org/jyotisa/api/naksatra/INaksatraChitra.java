/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 14. Chitra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraChitra extends INaksatra {


    @Override
    default int fid() {
        return 14;
    }

    @Override
    default String code() {
        return N14_CD;
    }

}
