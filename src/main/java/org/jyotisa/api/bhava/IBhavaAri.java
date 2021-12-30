/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 6.  Ari - Enemy, Disease, Debt, Law
 * <p>
 * Enemies, service, colleagues, opponents, accidents, acute illness, competitors,
 * trouble, debts, hospitals, theft, loss, prison, court, offence, servants or assistants, salaried employees.
 * <p>
 * Signifikators:
 * - Mangala - hostility, injury, theft
 * - Shani – Diseases
 * <p>
 * It also shows the capacity to overcome those obstacles and diseases and the capacity to win over
 * enemies, so it is usually related with doctors and healers, military, policeman, athletes and
 * physical strength, capacity to do big effort and hard work.
 * <p>
 * Usually the placement of the ruler of the 6th house and planets placed on the 6th house will show
 * the more difficult areas of life, areas where there may be more opposition and need to do a big
 * effort, and also the parts of the body that can be more prone to diseases, injuries and pains. More
 * specifically it is related to the digestive system and the immune system.
 * <p>
 * Spiritually it relates with Karma Yoga or selfless service and also to the capacity to do a constant
 * and steady effort, necessary for Sadhana or spiritual practices, self discipline and austerities.
 * <p>
 * It is also an important “Artha” house representing the capacity to work hard to attain the material
 * goals.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaAri extends IBhava {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return B06_CD;
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
    default boolean dusthana() {
        return true;
    }

    @Override
    default boolean trishadaya() {
        return true;
    }
}
