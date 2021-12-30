/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.nityayoga;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INityaYogaPreeti extends INityaYoga {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return NY02_CD;
    }

}
