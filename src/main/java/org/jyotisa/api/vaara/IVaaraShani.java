/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.vaara;

/**
 * 7. Shanivaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVaaraShani extends IVaara {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return SAVR_CD;
    }

}
