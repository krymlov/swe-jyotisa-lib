/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-11
*/

package org.jyotisa.api.graha;

import java.io.Serializable;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IGrahas extends Serializable {
    IGrahaEntity[] all();

    IGrahaEntity lagna();
    IGrahaEntity surya();
    IGrahaEntity chandra();
    IGrahaEntity mangala();
    IGrahaEntity budha();
    IGrahaEntity guru();
    IGrahaEntity shukra();
    IGrahaEntity shani();
    IGrahaEntity rahu();
    IGrahaEntity ketu();
    
    IGrahaEntity sweta();
    IGrahaEntity syama();
    IGrahaEntity teevra();
}
