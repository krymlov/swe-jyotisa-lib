/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 21. Uttarashadha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraUttaraAshadha extends INaksatra {


    @Override
    default int fid() {
        return 21;
    }

    @Override
    default String code() {
        return N21_CD;
    }

}
