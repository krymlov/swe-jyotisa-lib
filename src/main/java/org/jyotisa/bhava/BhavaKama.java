/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaKama;

/**
 * 7.  Kāma - Spouse, Partners, Yuvati (Kalatra vai Djaya) bhava
 * <p>
 * A partnership, mutual relations, business relations, deals, husband, wife,
 * long trips, links with foreign countries, courts, death, love, contracts, trade, diplomacy.
 * <p>
 * Signifikators:
 * - Shukra - wife
 * - Guru – husband
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaKama implements IBhavaKama {
    B7,
    KAM;

    @Override
    public IBhavaKama[] all() {
        return values();
    }
}
