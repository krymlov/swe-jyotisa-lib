/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.api;

import org.jyotisa.api.karaka.ICharaKarakaOption;

import java.io.Serializable;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IKundaliOptions extends Serializable {
    ICharaKarakaOption charaKarakaOption();
}
