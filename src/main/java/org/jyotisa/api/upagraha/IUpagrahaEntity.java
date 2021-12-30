/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.api.upagraha;

import org.jyotisa.api.IKundaliSequenceEntity;
import org.jyotisa.api.bhava.IBhava;

/**
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IUpagrahaEntity extends IKundaliSequenceEntity<IUpagraha> {
    IBhava bhava();
}
