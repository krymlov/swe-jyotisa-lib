/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 19. Moola
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraMula extends INaksatra {


    @Override
    default int fid() {
        return 19;
    }

    @Override
    default String code() {
        return N19_CD;
    }

}
