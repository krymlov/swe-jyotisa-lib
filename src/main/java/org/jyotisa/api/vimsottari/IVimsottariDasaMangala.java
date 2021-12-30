/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vimsottari;

/**
 * 3. Mangala vimsottari dasa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVimsottariDasaMangala extends IVimsottariDasa {
    
    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return MAVD_CD;
    }

    @Override
    default double length() {
        return 7;
    }
}
