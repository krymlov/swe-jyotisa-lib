/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaAri;

/**
 * 6.  Ari - Enemy, Disease, Debt, Law
 * <p>
 * Enemies, service, colleagues, opponents, accidents, acute illness, competitors,
 * trouble, debts, hospitals, theft, loss, prison, court, offence, servants or assistants, salaried employees.
 * <p>
 * Signifikators:
 * - Mangala - hostility, injury, theft
 * - Shani – Diseases
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaAri implements IBhavaAri {
    B6,
    ARI;

    @Override
    public IBhavaAri[] all() {
        return values();
    }

}
