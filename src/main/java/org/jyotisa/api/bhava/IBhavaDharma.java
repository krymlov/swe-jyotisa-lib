/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 9. Dharma - Natural Law, Good-fortune,
 * - House of the success (proper actions)
 * - Bhagya (Dharma) bhava
 * <p>
 * Success, higher knowledge, philosophy, gurus, long trips, pilgrimages, happiness,
 * religion, devotional service, meditation, prayer, father, God, happy destiny, proper actions,
 * ethics, law, divine love, higher education institutions, a link with the divine,
 * legislation, public authorities, chiefs, charity, justice, prosperity.
 * <p>
 * Signifikators:
 * - Guru - the proper actions, teachers
 * - Surya - father
 * <p>
 * This is considered the most auspicious house or the house of good luck.
 * <p>
 * Good luck is the result of following Dharma or right actions in the past.
 * Therefore planets placed in this house and the houses they rule will tend to flourish and manifest
 * their best qualities, unless they are debilitated or severely afflicted.
 * The planet ruling the 9th house is also a benefic planet which will bless the house it is placed or
 * aspecting, if it is not afflicted.
 * <p>
 * The 9th house is the main house to assess the spiritual inclinations of a person, their philosophical
 * values, spiritual path, religious approach, and the main dharma or mission in life, the kind of path
 * that will be conducing to the greatest benefit or growth.
 * <p>
 * It is also related with pilgrimages, successful travels for spiritual reasons and connections with
 * foreigners.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaDharma extends IBhava {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return B09_CD;
    }

    @Override
    default boolean trikona() {
        return true;
    }

    @Override
    default boolean apoklima() {
        return true;
    }
}
