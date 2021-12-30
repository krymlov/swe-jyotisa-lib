/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-11
 */

package org.jyotisa.gochara;


import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.graha.IGrahaEnumEntity;
import org.jyotisa.app.KundaliSequenceEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public class GrahaEntity extends KundaliSequenceEntity<IGraha> implements IGrahaEnumEntity {
    private static final long serialVersionUID = 5646756504412481376L;

    public GrahaEntity(final double longitude, final IGraha graha, final double julday) {
        super(longitude, graha, julday);
    }

}
