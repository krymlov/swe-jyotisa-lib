/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.api.karaka;

import org.jyotisa.api.graha.IGrahaEntity;

import java.util.List;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface ICharaKarakas {
    /**
     * @return true if seven karakas else eight
     */
    boolean isSevenKarakas();
    
    /**
     * @return ordered list of chara karakas
     */
    List<IGrahaEntity> getEntities();
}
