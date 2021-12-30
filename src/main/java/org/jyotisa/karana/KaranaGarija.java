/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaGarija;

/**
 * 5. Garija
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaGarija implements IKaranaGarija {
    KR5,
    GRJ;

    @Override
    public IKaranaGarija[] all() {
        return values();
    }
}
