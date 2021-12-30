/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaTaitula;

/**
 * 4. Taitula
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaTaitula implements IKaranaTaitula {
    KR4,
    TTL;

    @Override
    public IKaranaTaitula[] all() {
        return values();
    }
}
