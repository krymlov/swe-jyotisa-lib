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
public interface INityaYogaShubha extends INityaYoga {

    @Override
    default int fid() {
        return 23;
    }

    @Override
    default String code() {
        return NY23_CD;
    }

}
