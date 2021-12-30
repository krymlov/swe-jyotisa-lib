/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 3. Tritiya
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiTritiya extends ITithi {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return S03_CD;
    }

}
