/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api;

import org.jyotisa.api.naksatra.INaksatraPada;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface IKundaliSequenceEntity<E extends IKundaliSequence<E>> extends IKundaliEntity<E> {
    INaksatraPada pada();
}
