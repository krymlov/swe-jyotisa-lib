/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaMrityu;

/**
 * 7. Mrityu
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaMrityu implements IUpagrahaMrityu {
    UG7,
    MRT;

    @Override
    public IUpagrahaMrityu[] all() {
        return values();
    }
}
