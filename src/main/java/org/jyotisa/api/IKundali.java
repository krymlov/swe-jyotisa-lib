/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2019-12
 */

package org.jyotisa.api;


import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.graha.IGrahas;
import org.jyotisa.api.grahan.IGrahan;
import org.jyotisa.api.lagna.ILagnas;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.upagraha.IUpagrahas;
import org.jyotisa.api.varga.IVarga;

import java.io.Serializable;

/**
 * Jyotisha (ज्योतिष) is the traditional Indian system of astrology[1] rooted in Vedic-Purānic tradition. It is
 * often called Vedic Jyotiṣa by its practitioners and Hindu or Indian astrology by foreigners. Jyotiṣa is a
 * Vedānga or an auxiliary text to the the Veda.
 * <br><br>
 * Origins
 * <br>
 * Jyotiṣa is a Vedānga[3]. The first ever record for Jyotiṣa is found in the Veda. The earliest reference to
 * Jyotiṣa as a vedānga is found in the Mundaka Upanishad and Chāndogya Upanishada also mentions it as a
 * distinct discipline. The first Vedic Yajna in Yajurveda is Darsha-paurnamāsa Yajna, which needed correct
 * timing of tithis (eg, New Moon or Darsha, and Full Moon or Poornamāsa). Vedas are concerned with Yajnas
 * which can be performed only at astrologically auspicious moments.
 * <br><br>
 * Jyotiṣa has been referred to as the Eye of Veda, i.e., Eye of real Knowledge (Veda means real Knowledge),
 * because Jyotisha provides the tangible proofs in favour of existence of soul and rebirth by means of
 * horoscope in this life reflecting the karmas of past life.
 *
 * @author Yura Krymlov
 * @version 1.0, 2019-12
 */
public interface IKundali extends IKundaliContext, Serializable {
    IDignity dignity(final IVarga varga, final IGraha graha);
    IDignity dignity(final IGraha graha);

    IGraha chayaGraha(boolean rahu);
    IKundaliFields fields();
    IPanchanga panchanga();
    IUpagrahas upagrahas();
    ILagnas lagnas();
    IGrahas grahas();

    /**
     * Computes the next solar eclipse at a given geographical position or anywhere on earth
     *
     * @param backward - if true then search should be done backwards
     * @param locally  - if true then computes the solar eclipse at a given geographical position
     *                 else computes the next solar eclipse anywhere on earth
     */
    IGrahan suryaGrahan(boolean backward, boolean locally);

    /**
     * Computes the next lunar eclipse at a given geographical position or anywhere on earth
     *
     * @param backward - if true then search should be done backwards
     * @param locally  - if true then computes the solar eclipse at a given geographical position
     *                 else computes the next solar eclipse anywhere on earth
     */
    IGrahan chandraGrahan(boolean backward, boolean locally);

}
