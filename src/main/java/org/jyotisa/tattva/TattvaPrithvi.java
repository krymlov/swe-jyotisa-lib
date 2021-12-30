/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.tattva;

import org.jyotisa.api.graha.IGrahaBudha;
import org.jyotisa.api.tattva.ITattvaPrithvi;

import static org.jyotisa.graha.budha.GrahaBudha.BUDHA;

/**
 * 3. PRITHVI
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum TattvaPrithvi implements ITattvaPrithvi {
    TT3,
    PRI;

    @Override
    public ITattvaPrithvi[] all() {
        return values();
    }

    @Override
    public IGrahaBudha lord() {
        return BUDHA;
    }
}
