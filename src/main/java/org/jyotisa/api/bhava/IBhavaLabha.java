/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 11. Labha - Gains, Profits
 * <p>
 * Earnings, income, pleasant options, cash turnower, friends, allies, winnings,
 * welfare, goals, ambitions, the image, business.
 * <p>
 * Signifikator:
 * - Guru - earnings
 * <p>
 * It is a “Kama” house, related with the aspirations and desires and the capacity to fulfill them.
 * It relates with abundance, money, income and possession of the desired objects.
 * Planets placed in the 11th house tend to give good results and the houses they rule prosper and
 * develop their potentials.
 * <p>
 * Planets in this house and the connection they make with other houses show the means of income
 * of the native, their main way to make money or gain, and can also be an important factor in
 * determine the profession and work of a person, together with the 10th house and the ascendant.
 * <p>
 * It is also related with the social environment of the person, groups of people, the close friends and
 * siblings, mainly the elder siblings.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaLabha extends IBhava {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return B11_CD;
    }

    @Override
    default boolean panapara() {
        return true;
    }

    @Override
    default boolean upachaya() {
        return true;
    }

    @Override
    default boolean trishadaya() {
        return true;
    }
}
