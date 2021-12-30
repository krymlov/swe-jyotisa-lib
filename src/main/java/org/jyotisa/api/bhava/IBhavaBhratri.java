/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 3.  Bhratri - Siblings, Intentions, Sahaja bhava
 * <p>
 * Decisiveness, courage, younger brothers and sisters, neighbors, determination,
 * the ability to realize expectations, intellectual ability, memory, strength, short trips,
 * leadership, communication, commerce, self-expression.
 * <p>
 * Signifikator:
 * - Mangala - energy and courage
 * <p>
 * This house represents desires, impulses, courage, ambitions, motivations, constant movement or
 * short trips, bothers and sisters (mainly younger) and close friends.
 * It is also the house of expression and communications and is related to artists, musicians, actors,
 * dancers, writers, journalists, the show business, the media and internet.
 * Physically represents physical strength, the upper limbs, arms, shoulders, elbows, wrists, hands
 * and the upper chest, lungs and upper breath passages.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaBhratri extends IBhava {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return B03_CD;
    }

    @Override
    default boolean apoklima() {
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
