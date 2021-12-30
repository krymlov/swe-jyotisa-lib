/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 10. Magha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraMagha extends INaksatra {


    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return N10_CD;
    }

}
