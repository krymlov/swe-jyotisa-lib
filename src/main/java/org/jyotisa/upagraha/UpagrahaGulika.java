/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaGulika;

/**
 * 10. Gulika
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaGulika implements IUpagrahaGulika {
    UG10,
    GLK;

    @Override
    public IUpagrahaGulika[] all() {
        return values();
    }
}
