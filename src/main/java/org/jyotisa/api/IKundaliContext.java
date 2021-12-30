/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */
package org.jyotisa.api;

import org.swisseph.ISwissEph;
import org.swisseph.api.*;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IKundaliContext extends ISweContext {
    @Override
    default ISweObjectsSequence sweSequence() {
        return sweObjects().sweSequence();
    }

    @Override
    default ISweObjectsOptions sweOptions() {
        return sweObjects().sweOptions();
    }

    @Override
    default ISweGeoLocation sweLocation() {
        return sweObjects().sweLocation();
    }

    @Override
    default ISweJulianDate sweJulianDate() {
        return sweObjects().sweJulianDate();
    }

    @Override
    default ISwissEph swissEph() {
        return sweObjects().swissEph();
    }

    IKundaliOptions getOptions();
    ISweObjects sweObjects();
}
