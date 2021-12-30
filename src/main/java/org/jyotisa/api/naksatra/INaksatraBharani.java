/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 2 Bharani
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraBharani extends INaksatra {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return N02_CD;
    }

}
