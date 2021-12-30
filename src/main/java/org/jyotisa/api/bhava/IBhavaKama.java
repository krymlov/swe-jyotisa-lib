/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 7.  Kāma - Spouse, Partners, Yuvati (Kalatra vai Djaya) bhava
 * <p>
 * A partnership, mutual relations, business relations, deals, husband, wife,
 * long trips, links with foreign countries, courts, death, love, contracts, trade, diplomacy.
 * <p>
 * Signifikators:
 * - Shukra - wife
 * - Guru – husband
 * <p>
 * The 7th house is the opposite of the ascendant. Therefore it represents what the individual is
 * looking to complement him/herself. Opposites attract.
 * The 7th house represents the “other person” the opposite but complementary to the self.
 * All maters related to the marriage, sexuality and important partnerships in life are seen in this
 * house.
 * <p>
 * It is a “Kama” house, related with the sexual desires and passions, and the desire or impulse to
 * have committed relationships.
 * <p>
 * This house is also related with trade, commerce, business partners and client kind of
 * relationships, and it also represents traveling and locations other than the one the individual is
 * placed and foreign trade.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaKama extends IBhava {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return B07_CD;
    }

    @Override
    default boolean kendra() {
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
