/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.karana;


import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.karana.IKaranaEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class KaranaEntity extends KundaliSequenceEntity<IKarana> implements IKaranaEntity {
    private static final long serialVersionUID = 7833484901023652010L;

    public KaranaEntity(final double offset, final IKarana karanaEnum, final double julday) {
        super(offset, karanaEnum, julday);
    }
}
