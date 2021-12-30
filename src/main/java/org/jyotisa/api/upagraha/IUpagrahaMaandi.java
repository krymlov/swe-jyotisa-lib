/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 11. Maandi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaMaandi extends IUpagraha {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return UG11_CD;
    }

}
