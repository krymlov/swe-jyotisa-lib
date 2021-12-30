/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.karana;

import org.jyotisa.api.karana.IKaranaKimstughna;

/**
 * 11. Kimstughna
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum KaranaKimstughna implements IKaranaKimstughna {
    KR11,
    KSG;

    @Override
    public IKaranaKimstughna[] all() {
        return values();
    }
}
