/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 8. Pushya
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraPushya extends INaksatra {


    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return N08_CD;
    }

}
