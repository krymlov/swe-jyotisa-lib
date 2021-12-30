/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaUpaketu;

/**
 * 5. Upaketu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaUpaketu implements IUpagrahaUpaketu {
    UG5,
    UPK;

    @Override
    public IUpagrahaUpaketu[] all() {
        return values();
    }
}
