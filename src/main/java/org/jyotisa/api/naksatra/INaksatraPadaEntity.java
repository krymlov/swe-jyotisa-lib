/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.naksatra;

import org.jyotisa.api.IKundaliSequenceEntity;
import org.jyotisa.api.graha.IGraha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2020-12
 */
public interface INaksatraPadaEntity extends IKundaliSequenceEntity<INaksatraPada> {
    IGraha graha();
}
