/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.tattva;

/**
 * 4. VAYU
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITattvaVayu extends ITattva {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return TT4_CD;
    }

}
