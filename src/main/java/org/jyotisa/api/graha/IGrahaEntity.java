/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.api.graha;

import org.jyotisa.api.IKundaliSequenceEntity;
import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.varga.IVarga;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IGrahaEntity extends IKundaliSequenceEntity<IGraha> {
    void charaKaraka(ICharaKaraka charaKaraka);
    ICharaKaraka charaKaraka();

    IDignity dignity();
    IDignity dignity(IVarga varga);

    double latitude();

    /**
     * The Sanskrit name for retrograde planets is known as Vakri.
     * Vakri means twisted, crooked, winding, roundabout, indirect, evasive and ambiguous.
     */
    boolean vakri();

    IBhava bhava();
}
