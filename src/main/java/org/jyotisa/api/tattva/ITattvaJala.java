/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.tattva;

/**
 * 5. JALA
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITattvaJala extends ITattva {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return TT5_CD;
    }

}
