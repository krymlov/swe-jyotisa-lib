/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.nityayoga;


import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.nityayoga.INityaYogaEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public class NityaYogaEntity extends KundaliSequenceEntity<INityaYoga> implements INityaYogaEntity {
    private static final long serialVersionUID = 457236220459111319L;

    public NityaYogaEntity(final double longitude, final INityaYoga nityaYoga, final double julday) {
        super(longitude, nityaYoga, julday);
    }
}
