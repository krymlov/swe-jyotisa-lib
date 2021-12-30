/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api;

import java.util.Iterator;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IKundaliIterator<E extends IKundaliEntity<?>> extends Iterator<E> {

    /**
     * Special preset of flags for transit-longitude calculation
     */
    int transitCalcFlags();
}
