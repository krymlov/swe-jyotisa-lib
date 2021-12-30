/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaMatri;

/**
 * 4.  Matri - Relatives (Moksha), House of Mother
 * <p>
 * Mother, feelings, native house, real estate, education, knowledge, a place to shelter,
 * clothing, resources for transportation, emotion, happiness overall, comfort, hobbies.
 * <p>
 * Signifikator:
 * - Chandra - mind, emotions, mother
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaMatri implements IBhavaMatri {
    B4,
    MTR;

    @Override
    public IBhavaMatri[] all() {
        return values();
    }
}
