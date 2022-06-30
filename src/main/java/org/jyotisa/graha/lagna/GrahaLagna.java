/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-10
 */

package org.jyotisa.graha.lagna;

import org.jyotisa.api.graha.IGrahaLagna;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-10
 */
public enum GrahaLagna implements IGrahaLagna {
    G0, LG, LAGNA, As, Ascendant;

    @Override
    public IGrahaLagna[] all() {
        return values();
    }

}
