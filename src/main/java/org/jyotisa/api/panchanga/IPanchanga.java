/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.api.panchanga;

import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.api.vaara.IVaara;

import java.io.Serializable;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IPanchanga extends Serializable {
    double chandraLongitude();
    double suryaLongitude();

    INaksatraPada pada();
    INityaYoga yoga();
    IKarana karana();
    IVaara vaara();
    ITithi tithi();
}
