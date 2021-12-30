/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.api.upagraha;

import java.io.Serializable;

/**
 * @author  Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IUpagrahas extends Serializable {
    IUpagrahaEntity[] all();
    
    IUpagrahaEntity dhuma();
    IUpagrahaEntity vyatipaata();
    IUpagrahaEntity parivesha();
    IUpagrahaEntity indrachaapa();
    IUpagrahaEntity upaketu();
    
    /**
     * Kaala is a malefic upagraha similar to Sun.
     * Kaala rises at the middle of Sun’s part. In other words, we find the time at the middle of Sun’s part
     * and find lagna rising then. That gives Kaala’s longitude
     */
    IUpagrahaEntity kaala();
    
    /**
     * Mrityu is a malefic upagraha similar to Mars.
     * Mrityu rises at the middle of Mars’s part
     */
    IUpagrahaEntity mrityu();
    
    /**
     * Arthaprahaara is similar to Mercury
     * Artha Praharaka rises at the middle of Mercury’s part
     */
    IUpagrahaEntity arthaprahaara();
    
    /**
     * Yamaghantaka is similar to Jupiter
     * Yama Ghantaka rises at the middle of Jupiter’s part
     */
    IUpagrahaEntity yamaghantaka();
    
    /**
     * Gulika and Maandi are similar to Saturn
     * Gulika rises at the middle of Saturn’s part
     */
    IUpagrahaEntity gulika();
    
    /**
     * Gulika and Maandi are similar to Saturn
     * Maandi rises at the beginning of Saturn’s part
     */
    IUpagrahaEntity maandi();
}
