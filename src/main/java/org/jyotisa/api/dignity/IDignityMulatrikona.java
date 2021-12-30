/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 9 Mulatrikona
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityMulatrikona extends IDignity {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return MLT_CD;
    }

    @Override
    default int power() {
        return 75;
    }
}
