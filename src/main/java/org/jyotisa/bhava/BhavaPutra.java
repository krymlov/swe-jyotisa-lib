/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.bhava;

import org.jyotisa.api.bhava.IBhavaPutra;

/**
 * 5.  Putra - Children, Intelligence
 * <p>
 * Children, intelligence, romance, pregnancy, theoretical reflection, speculation,
 * business skills, mental practices, spiritual life, wisdom, knowledge of the scriptures,
 * the impact of past life, entertainment, sports, profession, creativity, investment
 * <p>
 * Signifikator:
 * - Guru - children, creativity, intelligence
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum BhavaPutra implements IBhavaPutra {
    B5,
    PTR;

    @Override
    public IBhavaPutra[] all() {
        return values();
    }
}
