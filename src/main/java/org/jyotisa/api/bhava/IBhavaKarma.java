/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.bhava;

/**
 * 10. Karma - Actions, Occupation
 * <p>
 * Career, status, purpose in life, profession, popularity, reputation, ambition, authority
 * <p>
 * Signifikators:
 * - Buddha -  profession, commerce
 * - Guru -  profession
 * - Surya - fame, prestige
 * <p>
 * It is the house where we can assess matters related to the success or failure in career, natural
 * vocation and talents, name, fame, prestige, honor and recognition in our work.
 * It represents the position and power that a person has in the society, related with his work
 * achievements.
 * <p>
 * The 10th house is the highest position in the sky at the moment of birth, so it represents our
 * direction and goals in life, the personal mission or work that we are supposed to do in this life.
 * <p>
 * Is an “Artha” house, related with prosperity and material security.
 * The 10th house is considered the strongest “Kendra” or angular house, being an important
 * “pillar” in supporting the strength of the chart as a whole.
 * <p>
 * Planets placed on the 10th house tend to be very important, strong and externally visible and
 * manifested in the life of a person, and tend to make the person ambitious, out-going and
 * achievement oriented.
 * <p>
 * The nature of those planes and the house they rule will be important influential factors to
 * determine the career and profession a person does in life.
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IBhavaKarma extends IBhava {

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return B10_CD;
    }

    @Override
    default boolean kendra() {
        return true;
    }

    @Override
    default boolean upachaya() {
        return true;
    }
}
