/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 26. Uttarabhadrapada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraUttaraBhadrapada extends INaksatra {


    @Override
    default int fid() {
        return 26;
    }

    @Override
    default String code() {
        return N26_CD;
    }

}
