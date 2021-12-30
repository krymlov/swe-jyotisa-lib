/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 7. Mrityu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaMrityu extends IUpagraha {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return UG07_CD;
    }

}
