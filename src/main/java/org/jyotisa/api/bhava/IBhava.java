/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

import org.jyotisa.api.IKundaliSequence;

import static org.swisseph.api.ISweConstants.BHAVA_LENGTH;
import static org.swisseph.api.ISweConstants.D100_BHAVA_LENGTH;
import static org.swisseph.utils.IDegreeUtils.round;
import static org.swisseph.utils.IModuloUtils.modulo;

/**
 * https://www.astronethra.com/post/classification-of-houses
 * @author Yura Krymlov
 * @version 1.0, 2019-11
 */
public interface IBhava extends IKundaliSequence<IBhava> {
    static double progress(final double longitude) {
        return round(modulo(BHAVA_LENGTH, longitude) * D100_BHAVA_LENGTH,2);
    }

    @Override
    default double length() {
        return BHAVA_LENGTH;
    }

    /**
     * Trikona or Trines (1st, 5th, 9th) houses (Lakshmi Sthanas)
     * @return true if Trikona
     */
    default boolean trikona() {
        return false;
    }

    /**
     * Kendra or Angular (1st, 4th, 7th, 10th) houses (Vishnu Sthanas)
     * @return true if kendra
     */
    default boolean kendra() {
        return false;
    }

    /**
     * Apoklima or Cadent (3rd, 6th, 9th, 12th) houses
     * @return true if apoklima
     */
    default boolean apoklima() {
        return false;
    }

    /**
     * Upachaya or Increasing (3rd, 6th, 10th, 11th) houses
     * @return true if upachaya
     */
    default boolean upachaya() {
        return false;
    }

    /**
     * Apachaya or Decreasing (1st, 2nd, 4th, 7th and 8th) houses
     * @return true if apachaya
     */
    default boolean apachaya() {
        return false;
    }

    /**
     * Dusthana or Evil (6th, 8th, 12th) houses
     * @return true if dusthana
     */
    default boolean dusthana() {
        return false;
    }

    /**
     * Panapara or Succedent (2nd, 5th, 8th, 11th) houses
     * @return true if panapara
     */
    default boolean panapara() {
        return false;
    }

    /**
     * The fourth and the eighth are called as Chaturasra houses.
     * @return true if chaturasra
     */
    default boolean chaturasra() {
        return false;
    }

    /**
     * Maraka or Death-Inflicting (2nd and 7th) houses
     * @return true if maraka
     */
    default boolean maraka() {
        return false;
    }

    /**
     * Trishadaya or Evil Houses. The  group of 3rd house, 6th house and 11th house
     * represents the three evil factors i.e. desire, anger and greed respectively.
     * @return true if trishadaya
     */
    default boolean trishadaya() {
        return false;
    }

    // Bhavas
    /**
     * 1.  Tanu - Ascendant, Self, Body
     */
    String B01_CD = "B1";
    /**
     * 2.  Dhana - Wealth, Speech, Family
     */
    String B02_CD = "B2";
    /**
     * 3.  Bratri - Siblings, Intentions
     */
    String B03_CD = "B3";
    /**
     * 4.  Matri - Relatives (Moksha)
     */
    String B04_CD = "B4";
    /**
     * 5.  Putra - Children, Intelligence
     */
    String B05_CD = "B5";
    /**
     * 6.  Ari   - Enemy, Disease, Debt, Law
     */
    String B06_CD = "B6";
    /**
     * 7.  Kāma   - Spouse, Partners
     */
    String B07_CD = "B7";
    /**
     * 8.  Mrityu - Ayur, Death (Moksha)
     */
    String B08_CD = "B8";
    /**
     * 9.  Dharma - Natural Law, Good-fortune
     */
    String B09_CD = "B9";
    /**
     * 10. Karma - Actions, Occupation
     */
    String B10_CD = "B10";
    /**
     * 11. Labha - Gains, Profits
     */
    String B11_CD = "B11";
    /**
     * 12. Vyaya - Loss (Moksha)
     */
    String B12_CD = "B12";
}
