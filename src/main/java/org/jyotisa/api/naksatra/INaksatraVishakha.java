/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 16. Visakha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraVishakha extends INaksatra {


    @Override
    default int fid() {
        return 16;
    }

    @Override
    default String code() {
        return N16_CD;
    }

}
