/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 18. Jyeshtha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraJyeshtha extends INaksatra {


    @Override
    default int fid() {
        return 18;
    }

    @Override
    default String code() {
        return N18_CD;
    }

}
