/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 24. Shatabisha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraShatabhisha extends INaksatra {


    @Override
    default int fid() {
        return 24;
    }

    @Override
    default String code() {
        return N24_CD;
    }

}
