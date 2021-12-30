/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 1. Tanu - Ascendant, Self, Body house
 * <p>
 * The physical "I", personal character, viability, main tendencies, general wel-being,
 * temperament, health, main orientation of life, fame, success
 * <p>
 * Signifikator: Surya - "I"
 * <p>
 * The 1st house represents: the Self, the self identity, character, personality, the main energy that
 * impulses the starting of a life or an event, the beginning, the birth and early infancy and its
 * condition, the physical body and its health, strengths or weaknesses and its physical appearance.
 * The condition and strength of the first house and its ruling planet is a very important factor for
 * the general health and vitality of the individual.
 * Within the physical body, it specifically it represents the area of the head, including heir and
 * brain.
 * <p>
 * It also shows important personality trends and how the individual projects himself on a public
 * level, the public appearance.
 * <p>
 * The Sign and the planets placed or aspecting this house as well as the planet ruling the 1st house
 * and the house it is placed on also has an important effect on the personality, sense of dharma and
 * career propensities.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaTanu extends IBhava {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return B01_CD;
    }

    @Override
    default boolean trikona() {
        return true;
    }

    @Override
    default boolean kendra() {
        return true;
    }

    @Override
    default boolean apachaya() {
        return true;
    }
}
