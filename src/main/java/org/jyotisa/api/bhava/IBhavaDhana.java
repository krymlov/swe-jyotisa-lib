/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 2.  Dhana - Wealth, Speech, Family, Savings house
 * <p>
 * Property and financial accumulation, food, language, benefits, securities,
 * family life, donations, education.
 * <p>
 * Signifikators:
 * - Guru - the ability to make money
 * - Buddha - childhood, language, education
 * <p>
 * It also represents the mouth and food, the way we eat and drink and what kind of foods one likes,
 * speech, languages and the capacity for verbal communication, the family, family inheritance,
 * money of the family and childhood.
 * <p>
 * Physically it represents the face, mouth, throat, neck and thyroid gland, right eye, teeth, tongue,
 * nose, nails.
 * <p>
 * It also shows inner potentials.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaDhana extends IBhava {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return B02_CD;
    }

    @Override
    default boolean panapara() {
        return true;
    }

    @Override
    default boolean maraka() {
        return true;
    }

    @Override
    default boolean apachaya() {
        return true;
    }
}
