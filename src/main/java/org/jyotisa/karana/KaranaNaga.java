/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaNaga;

/**
 * 10. Naga
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaNaga implements IKaranaNaga {
    KR10,
    NAG;

    @Override
    public IKaranaNaga[] all() {
        return values();
    }
}
