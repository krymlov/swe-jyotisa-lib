/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.avastha;

import org.jyotisa.api.avastha.IAvasthaMrita;

/**
 * 5.  Mrita - the dead - spent, with least effect
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum AvasthaMrita implements IAvasthaMrita {
    AV5,
    MRITA;

    @Override
    public IAvasthaMrita[] all() {
        return values();
    }
}
