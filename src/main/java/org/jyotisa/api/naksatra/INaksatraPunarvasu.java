/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 7. Punarvasu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraPunarvasu extends INaksatra {


    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return N07_CD;
    }

}
