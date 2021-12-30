/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 9. Ashlesha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraAshlesha extends INaksatra {


    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return N09_CD;
    }

}
