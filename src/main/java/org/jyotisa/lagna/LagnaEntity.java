/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.lagna;


import org.jyotisa.api.lagna.ILagna;
import org.jyotisa.api.lagna.ILagnaEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.1, 2019-12
 */
public class LagnaEntity extends KundaliSequenceEntity<ILagna> implements ILagnaEntity {
    private static final long serialVersionUID = -6537471937476912665L;

    public LagnaEntity(final double longitude, final ILagna lagna, final double julday) {
        super(longitude, lagna, julday);
    }

}
