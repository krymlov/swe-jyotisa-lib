/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaIndrachaapa;

/**
 * 4. Indrachaapa
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaIndrachaapa implements IUpagrahaIndrachaapa {
    UG4,
    CHP;

    @Override
    public IUpagrahaIndrachaapa[] all() {
        return values();
    }
}
