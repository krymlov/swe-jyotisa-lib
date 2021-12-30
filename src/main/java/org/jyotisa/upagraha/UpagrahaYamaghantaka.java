/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaYamaghantaka;

/**
 * 9. Yamaghantaka
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaYamaghantaka implements IUpagrahaYamaghantaka {
    UG9,
    YAM;

    @Override
    public IUpagrahaYamaghantaka[] all() {
        return values();
    }
}
