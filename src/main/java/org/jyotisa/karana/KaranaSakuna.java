/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaSakuna;

/**
 * 8. Sakuna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaSakuna implements IKaranaSakuna {
    KR8,
    SKN;

    @Override
    public IKaranaSakuna[] all() {
        return values();
    }
}
