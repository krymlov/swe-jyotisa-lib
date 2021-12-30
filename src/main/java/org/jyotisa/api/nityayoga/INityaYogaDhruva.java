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
public interface INityaYogaDhruva extends INityaYoga {

    @Override
    default int fid() {
        return 12;
    }

    @Override
    default String code() {
        return NY12_CD;
    }

}
