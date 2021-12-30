/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 12. Vyaya - Loss (Moksha), House of the liberation
 * <p>
 * Liberation, loss, distant or a mysterious places, enlightenment, physical pleasures,
 * places which restrict the freedom (hospital, prison, ashram, monastery, nursing homes),
 * failure, poverty, indifference to the material world, renunciation of secular life,
 * generosity, self-sacrifice, spiritual paths, profession, life after death,
 * the previous incarnation, the end of life, situation after death, the next life,
 * problems, secret enemies, deportation, extravagance, vice, accidents, shame, scandals,
 * betrayal, emigration, the quest for spirituality.
 * <p>
 * Signifikators:
 * - Shani - losses
 * - Ketu - liberation
 * <p>
 * It is the last of the houses, so, it represents the endings, the dissolution, the moment when the
 * individuality is lost and everything returns back to the source.
 * It represents isolation, seclusion, and liberation from the material attachments.
 * It represents what we have to detach from in this life and our main source of expenses or material
 * loss.
 * <p>
 * If this house and its ruler are afflicted it can cause material loss, separation, feeling of loneliness,
 * depression, mental sorrow, isolation, imprisonment, hospitalization, addictions, lack of success in
 * undertakings, loss of dear objects and people.
 * On the other hand, this is the most important house from the spiritual point of view.
 * It is the house of “Moksha” or attainment of liberation from the cycle of births and deaths, pain
 * and material attachments, to return to the source where the soul came from, the God
 * consciousness.
 * <p>
 * This house represents the opposite to materialism. It is the moment when the material dissolves
 * and returns to the origin, the spirit, the pure consciousness.
 * This house is related with monks, people who search for God and enlightenment, the capacity to
 * detach from the material illusion to become one with the divine.
 * It relates to non-profit organizations, actions done without any expectations for material reward,
 * charity, giving, generosity, desire to be in secluded places out of the worldly noise, ashrams and
 * monasteries, places of prayer and meditation, spiritual and mystical experiences.
 * This house is related to “loosing oneself”, loosing the individuality or the “ego” to find the
 * oneness with God.
 * <p>
 * But if this house is severely afflicted and there are other indications of weakness of the mind in
 * the chart, it can lead a person to desire to loose his ego and personality trough unhealthy and un-
 * spiritual methods like addiction to alcohol, drugs, sex, being subjugated or enslaved by other
 * people, loss of will power and weakness of personality.
 * Planets placed on the 12th house tend to loose their strength and capacity to express their energy
 * in the material level, even though they can be vary favorable for the spiritual practices or for
 * success in foreign countries.
 * <p>
 * Important planets like the Ascendant lord, the Moon or the sun on the 12th house can bring a
 * sense of dissatisfaction with life, a feeling that something is missing or a sense of not belonging
 * to, which can create a feeling if loneliness or isolation, but this is also a strong call to search for
 * the real source of happiness, which is on the Divine.
 * It really shows an advanced Soul who is ready to move into a higher spiritual realization. Once
 * established on a spiritual path and finding inner peace, they can also be successful in other
 * activities of life.
 * <p>
 * If those planets represent people, like parents, children, spouse, etcetera, their placement on the
 * 12th house will tend to create a separation or loss of them or will indicate that this people may
 * have some personal difficulties.
 * <p>
 * This house is also relates with the “bed comforts” so it is related with the capacity to sleep well or
 * not, and the sexual enjoyments or difficulties.
 * The 12th house also represents exile, far away places, foreign countries, foreign people, long
 * distance travel, foreign trade, imports-exports, tourism, travel agencies and like;
 * places of isolation and seclusion, like hospitals, jails, monasteries, living in the nature, out of the
 * noise of the cities, are also ruled by the 12th house.
 * <p>
 * Physically it represents the feet.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaVyaya extends IBhava {

    @Override
    default int fid() {
        return 12;
    }

    @Override
    default String code() {
        return B12_CD;
    }

    @Override
    default boolean apoklima() {
        return true;
    }

    @Override
    default boolean dusthana() {
        return true;
    }
}
