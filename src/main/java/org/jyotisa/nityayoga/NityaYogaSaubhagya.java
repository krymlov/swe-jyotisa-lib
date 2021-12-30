/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.nityayoga;

import org.jyotisa.api.nityayoga.INityaYogaSaubhagya;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum NityaYogaSaubhagya implements INityaYogaSaubhagya {
    NY4,
    SAUB;

    @Override
    public INityaYogaSaubhagya[] all() {
        return values();
    }
}
