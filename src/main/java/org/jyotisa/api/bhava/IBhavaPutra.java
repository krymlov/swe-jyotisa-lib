/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 5.  Putra - Children, Intelligence
 * <p>
 * Children, intelligence, romance, pregnancy, theoretical reflection, speculation,
 * business skills, mental practices, spiritual life, wisdom, knowledge of the scriptures,
 * the impact of past life, entertainment, sports, profession, creativity, investment
 * <p>
 * Signifikator:
 * - Guru - children, creativity, intelligence
 * <p>
 * This house represents the capacity to have children, the relationship with them and their
 * wellbeing.
 * <p>
 * It also represents the capacity to be creative on a mental level, creative ideas, creative intelligence
 * or buddhi, writing and artistic creativity.
 * <p>
 * It is an auspicious house related with the “Purva punya” or past good karmas or merits and how
 * they can manifest in talents and blessings in this life.
 * It is a house of dharma, related with the sense of righteousness, justice, wisdom, education, the
 * process of learning, studying and teaching, educational institutions, books, writers, libraries,
 * sacred scriptures and Mantras.
 * <p>
 * Spiritually, it is an important house to determine the “Ishta Devata” or the deity or form of God
 * that the person has a natural connection with, coming from past life worship and natural mental
 * tendencies.
 * <p>
 * That house is also related with the capacity to play, games, child-like nature, sports,
 * entertainment and the romanticism in love relationships, recreation or what we like to do.
 * It also relates with good luck, fortune in speculation, gambling or investing.
 * <p>
 * Physically it represents the upper part of the abdomen, the stomach, liver, pancreas, and
 * pregnancies.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaPutra extends IBhava {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return B05_CD;
    }

    @Override
    default boolean trikona() {
        return true;
    }

    @Override
    default boolean panapara() {
        return true;
    }
}
