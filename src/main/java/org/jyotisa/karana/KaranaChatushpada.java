/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaChatushpada;

/**
 * 9. Chatushpada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaChatushpada implements IKaranaChatushpada {
    KR9,
    CSP;

    @Override
    public IKaranaChatushpada[] all() {
        return values();
    }
}
