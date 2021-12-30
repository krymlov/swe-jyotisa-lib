/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.upagraha;

/**
 * 3. Parivesha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IUpagrahaParivesha extends IUpagraha {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return UG03_CD;
    }

}
