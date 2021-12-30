/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 3 Adhisatru
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityAdhisatru extends IDignity {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return ADS_CD;
    }

    @Override
    default int power() {
        return 3;
    }
}
