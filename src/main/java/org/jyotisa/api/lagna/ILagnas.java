/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.api.lagna;


import java.io.Serializable;

/**
 * An important topic in addition to the topics I have covered for a student of astrology is – Special Lagnas
 * or Vishesika lagnas.<br>
 * <br>
 * Basic methodology remains the same – Treat the special lagna sign as the lagna and analyze all the planets
 * and Yogas from there, very much like you would analyze from the rising sign (ascendant)
 * 
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface ILagnas extends Serializable {
    ILagnaEntity[] all();

    /**
     * Janma Lagna (JL), the main Rising Sign/Ascendant used to mark the first house
     * of the (Whole Sign) Rasi Chakra in Vedic astrology
     */
    ILagnaEntity janma();

    /**
     * The Bhava Lagna is an alternate (Visesha) Lagna or Ascendant, which can be used to fine-tune the
     * analysis of a horoscope. It is a concept briefly described in Brihat Parashara Hora Shastra and in
     * Jaimini Sutram (Upadesa Sutra). Shri Parashara discusses it along with the Hora and Ghatika Lagnas. Most
     * Vedic astrologers don’t use the Bhava Lagna, and some believe that the concept of it is mistaken. The
     * shastra explains how to calculate it but not how to evaluate it.
     */
    ILagnaEntity bhava();

    /**
     * This lagna is to be utilized to analyze the financial status or well being of the individual. Treat the
     * sign in which HL is placed as the lagna and analyze the horoscope from it. Important is to see the
     * strength of the HL sign lord and placement as well as aspects on that lord and the sign. Also look at
     * the 2nd house and 11th house from the HL sign and their lords as well as their placement. The stronger
     * the better. When HL lord is exalted and well placed, especially in a non dusthana (6,8,12) position from
     * the HL sign, it bodes very well for the financial status of the individual.
     */
    ILagnaEntity hora();

    /**
     * This lagna is to be seen for analyzing the power and authority of an individual. Indirectly that can
     * also refer to the status of individual in society as well as his/ her rise in career.
     */
    ILagnaEntity ghati();

    /**
     * To be utilized exactly like HL explained in #1 above. It is used to see the financial well being of an
     * individual. Note that Planets placed in Kendra or Kona position from IL give good wealth in their dasas
     * whereas planets in 3,6,8,12th from IL will cause financial problems to an individual
     */
    //ILagna indu();

    //ILagna sree();
    //ILagna vighati();
    //ILagna varnada();
    //ILagna pranapada();
}
