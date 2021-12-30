/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaArthaprahaara;

/**
 * 8. Arthaprahaara
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaArthaprahaara implements IUpagrahaArthaprahaara {
    UG8,
    ART;

    @Override
    public IUpagrahaArthaprahaara[] all() {
        return values();
    }
}
