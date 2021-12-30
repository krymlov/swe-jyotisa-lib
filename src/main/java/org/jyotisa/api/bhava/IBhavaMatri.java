/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 4.  Matri - Relatives (Moksha), House of Mother
 * <p>
 * Mother, feelings, native house, real estate, education, knowledge, a place to shelter,
 * clothing, resources for transportation, emotion, happiness overall, comfort, hobbies.
 * <p>
 * Signifikator:
 * - Chandra - mind, emotions, mother
 * <p>
 * The fourth house represents what is underneath, the earth.
 * It represents the inner life, the mother, motherhood, the earth and Mother Nature, the home, the
 * education, property like houses, land, cattle, real estate and vehicles.
 * It also relates to the emotions, inner intimate feelings and inner happiness.
 * Physically represents the chest area, breast, milk and the heart.
 * <p>
 * Spiritually it is the house of devotion or Bhakti, love, emotional and mental peace and wellbeing
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaMatri extends IBhava {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return B04_CD;
    }

    @Override
    default boolean kendra() {
        return true;
    }

    @Override
    default boolean chaturasra() {
        return true;
    }

    @Override
    default boolean apachaya() {
        return true;
    }
}
