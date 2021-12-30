/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.dignity;

import org.jyotisa.api.dignity.IDignityMulatrikona;

/**
 * 9 Mulatrikona
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum DignityMulatrikona implements IDignityMulatrikona {
    DG9,
    MLT;

    @Override
    public IDignityMulatrikona[] all() {
        return values();
    }
}
