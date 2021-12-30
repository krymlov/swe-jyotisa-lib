/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaDhuma;

/**
 * 1. Dhuma
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaDhuma implements IUpagrahaDhuma {
    UG1,
    DHU;

    @Override
    public IUpagrahaDhuma[] all() {
        return values();
    }
}
