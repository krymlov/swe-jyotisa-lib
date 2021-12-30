package org.jyotisa.app;

import org.jyotisa.api.IKundaliEntity;
import org.jyotisa.api.IKundaliEnum;
import org.swisseph.app.SweEnumEntity;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public class KundaliEntity<E extends IKundaliEnum>
        extends SweEnumEntity<E> implements IKundaliEntity<E> {
    private static final long serialVersionUID = -886138658079318204L;

    public KundaliEntity(double longitude, E entityEnum, double julianDay) {
        super(longitude, entityEnum, julianDay);
    }

}
