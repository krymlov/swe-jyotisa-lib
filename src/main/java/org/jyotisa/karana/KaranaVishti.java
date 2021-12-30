/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaVishti;

/**
 * 7. Vishti
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaVishti implements IKaranaVishti {
    KR7,
    VST;

    @Override
    public IKaranaVishti[] all() {
        return values();
    }
}
