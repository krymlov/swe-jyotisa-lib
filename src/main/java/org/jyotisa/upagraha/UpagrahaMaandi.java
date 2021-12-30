/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaMaandi;

/**
 * 11. Maandi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaMaandi implements IUpagrahaMaandi {
    UG11,
    MND;

    @Override
    public IUpagrahaMaandi[] all() {
        return values();
    }
}
