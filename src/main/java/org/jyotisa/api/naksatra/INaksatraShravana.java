/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 22. Shravana
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraShravana extends INaksatra {


    @Override
    default int fid() {
        return 22;
    }

    @Override
    default String code() {
        return N22_CD;
    }

}
