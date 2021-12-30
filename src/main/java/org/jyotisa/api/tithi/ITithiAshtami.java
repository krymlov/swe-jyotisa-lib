/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 8. Ashtami
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiAshtami extends ITithi {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return S08_CD;
    }

}
