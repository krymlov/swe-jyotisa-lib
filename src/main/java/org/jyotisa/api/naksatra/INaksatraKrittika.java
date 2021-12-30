/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 3. Krittika
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraKrittika extends INaksatra {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return N03_CD;
    }

}
