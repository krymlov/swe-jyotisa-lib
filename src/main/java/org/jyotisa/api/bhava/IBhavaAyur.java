/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 8. Mrityu - Ayur, Death (Moksha), (Mrityu or Randhra) bhava
 * <p>
 * The unknown, transcendent, exploring the unknown, anything unusual, transformation,
 * length of the life, mysterious events, accidents, chronic illness, intuition, instincts,
 * scandals, shame, frustration, inability to forward  myself, unearned wealth, heritage,
 * excitement, vitality, sexual energy, fraud, abuse, death, everything associated with the end of life,
 * longevity, failure, suicide, violent deaths, secret life, pension, loans, gifts.
 * <p>
 * Signifikator:
 * - Shani - Death and longevity
 * <p>
 * It represents the hidden, unknown, mysterious and dark areas of life and the bad or difficult
 * karmas. It also represents the sexual organs and sexual energy, sexual magnetism or attraction.
 * <p>
 * If this house is afflicted it can lead to a short life span or chronic diseases, suffering, tragedies,
 * fear, mental depression, anguish, loss of dear objects or people, financial bankruptcy, crimes,
 * imprisonment or other forms of punishment, sexual aberrations, influence from low astral
 * entities, drug addictions, alcoholism or other forms of mental and physical degradation.
 * <p>
 * On the other hand, if this house is strong and well disposed, it can bring the opposite results,
 * meaning: long life, destruction of enemies and difficulties, knowledge of the mind and its hidden
 * potentials, knowledge of psychology, mathematics and sciences, awakening of psychic powers
 * and paranormal faculties, intuition, knowledge of secrets, esoterical knowledge like astrology and
 * tantra, proficiency in Yoga and the control of the mind and the astral body and attainment of
 * Moksha or spiritual liberation.
 * <p>
 * This house represents the “Kundalini” power.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaAyur extends IBhava {

    @Override
    default int fid() {
        return 8;
    }

    @Override
    default String code() {
        return B08_CD;
    }

    @Override
    default boolean panapara() {
        return true;
    }

    @Override
    default boolean dusthana() {
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
