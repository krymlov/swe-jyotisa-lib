/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.tithi;

import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.api.tithi.ITithiEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class TithiEntity extends KundaliSequenceEntity<ITithi> implements ITithiEntity {
    private static final long serialVersionUID = 1582805586221833418L;

    public TithiEntity(final double longitude, final ITithi tithiEnum, final double julday) {
        super(longitude, tithiEnum, julday);
    }
}
