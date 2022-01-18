/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.api.ekadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public interface IIndiraEkadasi extends IEkadasi {
    @Override
    default int fid() {
        return 21;
    }

    @Override
    default String code() {
        return EK21_CD;
    }
}
